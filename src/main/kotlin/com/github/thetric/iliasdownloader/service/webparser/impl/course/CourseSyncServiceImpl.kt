package com.github.thetric.iliasdownloader.service.webparser.impl.course

import com.github.thetric.iliasdownloader.service.IliasItemVisitor
import com.github.thetric.iliasdownloader.service.exception.CourseItemNotFoundException
import com.github.thetric.iliasdownloader.service.model.Course
import com.github.thetric.iliasdownloader.service.model.CourseFile
import com.github.thetric.iliasdownloader.service.model.CourseFolder
import com.github.thetric.iliasdownloader.service.model.IliasItem
import com.github.thetric.iliasdownloader.service.webparser.impl.IliasItemIdStringParsingException
import com.github.thetric.iliasdownloader.service.webparser.impl.course.jsoup.JSoupParserService
import com.github.thetric.iliasdownloader.service.webparser.impl.webclient.IliasWebClient
import org.apache.logging.log4j.LogManager
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Pattern

private const val COURSE_SELECTOR = "a[href*='_crs_'].il_ContainerItemTitle"
private val LAST_MODIFIED_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
private val COURSE_LINK_REGEX = Pattern.compile("""<a href="(?<url>.+)">(?<name>.+)</a>""")
private const val ROW_SEPARATOR = "  "

/**
 * [CourseSyncService] based on HTML parsing.
 */
class CourseSyncServiceImpl(
    private val jSoupParserService: JSoupParserService,
    private val webClient: IliasWebClient,
    iliasBaseUrl: String,
    clientId: String
) : CourseSyncService {
    private val courseOverview: String = iliasBaseUrl + "ilias.php?baseClass=ilPersonalDesktopGUI&cmd=jumpToSelectedItems"
    private val courseLinkPrefix: String = "${iliasBaseUrl}goto_${clientId}_crs_"
    private val courseWebDavPrefix: String = "${iliasBaseUrl}webdav.php/ilias-fhdo/ref_"
    private val log = LogManager.getLogger(CourseSyncServiceImpl::class.java)

    override val joinedCourses: Collection<Course>
        get() {
            log.info("Get all courses and groups from {}", courseOverview)
            val document = connectAndGetDocument(courseOverview)
            return getCoursesFromHtml(document)
        }

    private fun connectAndGetDocument(url: String): Document {
        val html = getHtml(url)
        return jSoupParserService.parse(html)
    }

    private fun getHtml(url: String): String {
        return webClient.getHtml(url)
    }

    private fun getCoursesFromHtml(document: Document): Collection<Course> {
        return document.select(COURSE_SELECTOR).map { toCourse(it) }
    }

    private fun toCourse(courseElement: Element): Course {
        val courseId = getCourseId(courseElement)
        val courseName = courseElement.text()
        val courseUrl = courseWebDavPrefix + courseId.toString() + "/"
        return Course(id = courseId, url = courseUrl, name = courseName)
    }

    private fun getCourseId(aTag: Element): Int {
        val href = aTag.attr("href")
        // href="http://www.ilias.fh-dortmund.de/ilias/goto_ilias-fhdo_crs_\d+.html"
        val idString = href.replaceFirst(courseLinkPrefix.toRegex(), "").replace(".html", "")
        // der Rest muss ein int sein
        return parseId(href, idString)
    }

    override fun visit(courseItem: IliasItem, itemVisitor: IliasItemVisitor): IliasItemVisitor.VisitResult {
        val itemContainer = getItemContainersFromUrl(courseItem.url)
        if (!itemContainer.isEmpty()) {
            return if (getNonEmptyEntries(itemContainer, courseItem).any {
                toIliasItem(courseItem, it, itemVisitor) == IliasItemVisitor.VisitResult.TERMINATE
            }) IliasItemVisitor.VisitResult.TERMINATE
            else IliasItemVisitor.VisitResult.CONTINUE
        }

        throw CourseItemNotFoundException("No items found at URL ", courseItem.url)
    }

    private fun getNonEmptyEntries(itemContainer: String, courseItem: IliasItem): List<String> {
        return getIliasItemRows(itemContainer, courseItem).filter { it.isBlank().not() }
    }

    /**
     * Extract HTML from the 'table'
     *
     * @param courseItem
     */
    private fun getIliasItemRows(tableHtml: String, courseItem: IliasItem): Collection<String> {
        val itemListStartDelimiter = "<hr>"
        val startIndexItemList = tableHtml.indexOf(itemListStartDelimiter)
        checkItemListIndex(startIndexItemList, "Begin", courseItem)

        val itemListEndDelimiter = "\n<hr>"
        val endIndexItemList = tableHtml.lastIndexOf(itemListEndDelimiter)
        checkItemListIndex(endIndexItemList, "End", courseItem)

        val itemListBeginPos = startIndexItemList + itemListStartDelimiter.length
        return if (itemListBeginPos >= endIndexItemList) {
            ArrayList()
        } else tableHtml.subSequence(itemListBeginPos, endIndexItemList)
            .split("\n")
            .map { it.trim() }
    }

    private fun checkItemListIndex(index: Int, name: String, item: IliasItem) {
        if (index == -1) {
            throw IllegalArgumentException(name + " of item list not found! Search URL is " + item.url)
        }
    }

    private fun getItemContainersFromUrl(itemUrl: String): String {
        val html = this.getHtml(itemUrl)
        val startTag = "<pre>"
        val startIndexTable = html.indexOf(startTag)
        val endTag = "</pre>"
        val endIndexTable = html.lastIndexOf(endTag)
        val exclusiveStartIndex = startIndexTable + startTag.length
        return html.substring(exclusiveStartIndex, endIndexTable - 1)
    }

    private fun toIliasItem(parent: IliasItem, itemRow: String, itemVisitor: IliasItemVisitor): IliasItemVisitor.VisitResult {
        if (isFolder(itemRow)) {
            val courseFolder = createFolder(parent, itemRow)

            val folderVisitResult = itemVisitor.handleFolder(courseFolder)
            return if (folderVisitResult == IliasItemVisitor.VisitResult.TERMINATE) {
                IliasItemVisitor.VisitResult.TERMINATE
            } else visit(courseFolder, itemVisitor)
        }

        // assume it is a file
        return itemVisitor.handleFile(createFile(parent, itemRow))
    }

    private fun isFolder(itemRow: String): Boolean {
        return itemRow[0] == '-'
    }

    private fun createFolder(parent: IliasItem, itemRow: String): CourseFolder {
        val firstPosSeparator = itemRow.indexOf(ROW_SEPARATOR)
        val secondPosSeparator = itemRow.indexOf(ROW_SEPARATOR, firstPosSeparator + ROW_SEPARATOR.length)
        val parsedLink = parseLink(itemRow, secondPosSeparator)
        return CourseFolder(
            name = parsedLink.name!!,
            url = resolveItemLink(parent, parsedLink.url!!),
            parent = parent)
    }

    private fun resolveItemLink(parent: IliasItem, relUrl: String): String {
        return parent.url + "/" + relUrl
    }

    private fun createFile(parent: IliasItem, itemRow: String): CourseFile {
        val firstPosSeparator = itemRow.indexOf(ROW_SEPARATOR)
        val secondPosSeparator = itemRow.indexOf(ROW_SEPARATOR, firstPosSeparator + ROW_SEPARATOR.length)

        val parsedLinkName = parseLink(itemRow, secondPosSeparator)
        return CourseFile(
            name = parsedLinkName.name!!,
            url = resolveItemLink(parent, parsedLinkName.url!!),
            parent = parent,
            modified = parseLastModified(itemRow, firstPosSeparator, secondPosSeparator),
            size = parseFileSize(itemRow, firstPosSeparator)
        )
    }

    private fun parseFileSize(itemRow: String, firstPosSeparator: Int): Int {
        val rawSizeInBytes = itemRow.subSequence(0, firstPosSeparator - 1)
        val sanitizedSizeInBytes = rawSizeInBytes.replace(",".toRegex(), "")
        return java.lang.Integer.parseInt(sanitizedSizeInBytes)
    }

    private fun parseLink(itemRow: String, secondPosSeparator: Int): ParsedIliasTableRow {
        val startIndex = secondPosSeparator + ROW_SEPARATOR.length
        val matcher = COURSE_LINK_REGEX.matcher(itemRow.subSequence(startIndex, -1))
        if (!matcher.matches()) {
            throw IllegalStateException("Failed to parse " + itemRow)
        }
        return ParsedIliasTableRow(matcher.group("name"), matcher.group("url"))
    }

    private fun parseLastModified(itemRow: String, firstPosSeparator: Int, secondPosSep: Int): LocalDateTime {
        val startIndex = firstPosSeparator + ROW_SEPARATOR.length
        val lastModifiedString = itemRow.substring(startIndex, secondPosSep - 1)
        return LocalDateTime.parse(lastModifiedString, LAST_MODIFIED_FORMATTER)
    }

    private fun parseId(href: String, probableIdString: String): Int {
        try {
            return Integer.parseInt(probableIdString)
        } catch (e: NumberFormatException) {
            throw IliasItemIdStringParsingException("Failed to parse \'$probableIdString\', original string was \'$href\'", e)
        }
    }
}
