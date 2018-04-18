package com.github.thetric.iliasdownloader.service.webparser.impl.course

import com.github.thetric.iliasdownloader.service.IliasItemVisitor
import com.github.thetric.iliasdownloader.service.exception.CourseItemNotFoundException
import com.github.thetric.iliasdownloader.service.model.Course
import com.github.thetric.iliasdownloader.service.model.IliasItem
import com.github.thetric.iliasdownloader.service.webparser.impl.course.jsoup.JSoupParserService
import com.github.thetric.iliasdownloader.service.webparser.impl.webclient.IliasWebClient
import mu.KotlinLogging
import org.jsoup.nodes.Document

private const val COURSE_SELECTOR = "a[href*='_crs_'].il_ContainerItemTitle"


/**
 * [CourseSyncService] based on HTML parsing.
 */
class CourseSyncServiceImpl(
    private val jSoupParserService: JSoupParserService,
    private val webClient: IliasWebClient,
    private val itemParser: IliasItemParser,
    private val courseOverview: String
) : CourseSyncService {
    private val log = KotlinLogging.logger {}

    override val joinedCourses: Collection<Course>
        get() {
            log.info { "Get all courses and groups from $courseOverview" }
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
        return document.select(COURSE_SELECTOR)
            .map { itemParser.parseCourse(it) }
    }

    override fun visit(
        courseItem: IliasItem,
        itemVisitor: IliasItemVisitor
    ): IliasItemVisitor.VisitResult {
        val itemContainer = getItemContainersFromUrl(courseItem.url)
        if (!itemContainer.isEmpty()) {
            return getNonEmptyEntries(itemContainer, courseItem)
                .stream()
                .map {
                    walkIliasItemNode(
                        courseItem,
                        it,
                        itemVisitor
                    )
                }
                .filter { it == IliasItemVisitor.VisitResult.TERMINATE }
                .findFirst()
                .orElse(IliasItemVisitor.VisitResult.CONTINUE)
        }

        throw CourseItemNotFoundException(
            "No items found at URL ",
            courseItem.url
        )
    }

    private fun getItemContainersFromUrl(itemUrl: String): String {
        val html = getHtml(itemUrl)
        val startTag = "<pre>"
        val startIndexTable = html.indexOf(startTag)
        val endTag = "</pre>"
        val endIndexTable = html.lastIndexOf(endTag)
        val exclusiveStartIndex = startIndexTable + startTag.length
        return html.substring(exclusiveStartIndex, endIndexTable - 1)
    }

    private fun getNonEmptyEntries(
        itemContainer: String,
        courseItem: IliasItem
    ): List<String> {
        return getIliasItemRows(itemContainer, courseItem).filter {
            it.isBlank().not()
        }
    }

    private fun getIliasItemRows(
        tableHtml: String,
        courseItem: IliasItem
    ): Collection<String> {
        val itemListStartDelimiter = "<hr>"
        val startIndexItemList = tableHtml.indexOf(itemListStartDelimiter)
        checkItemListIndex(startIndexItemList, "Begin", courseItem)

        val itemListEndDelimiter = "\n<hr>"
        val endIndexItemList = tableHtml.lastIndexOf(itemListEndDelimiter)
        checkItemListIndex(endIndexItemList, "End", courseItem)

        val itemListBeginPos =
            startIndexItemList + itemListStartDelimiter.length
        return if (itemListBeginPos >= endIndexItemList) {
            ArrayList()
        } else tableHtml.subSequence(itemListBeginPos, endIndexItemList)
            .split("\n")
            .map { it.trim() }
    }

    private fun checkItemListIndex(index: Int, name: String, item: IliasItem) {
        if (index == -1) {
            throw IllegalArgumentException("$name of item list not found! Search URL is ${item.url}")
        }
    }

    private fun walkIliasItemNode(
        parent: IliasItem,
        itemRow: String,
        itemVisitor: IliasItemVisitor
    ): IliasItemVisitor.VisitResult {
        if (itemParser.isFolder(itemRow)) {
            val courseFolder = itemParser.parseFolder(parent, itemRow)

            val folderVisitResult = itemVisitor.handleFolder(courseFolder)
            return if (folderVisitResult == IliasItemVisitor.VisitResult.TERMINATE) {
                IliasItemVisitor.VisitResult.TERMINATE
            } else visit(courseFolder, itemVisitor)
        }

        // assume it is a file
        return itemVisitor.handleFile(itemParser.parseFile(parent, itemRow))
    }

}
