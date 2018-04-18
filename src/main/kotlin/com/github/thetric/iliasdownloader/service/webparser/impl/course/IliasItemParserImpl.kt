package com.github.thetric.iliasdownloader.service.webparser.impl.course

import com.github.thetric.iliasdownloader.service.model.Course
import com.github.thetric.iliasdownloader.service.model.CourseFile
import com.github.thetric.iliasdownloader.service.model.CourseFolder
import com.github.thetric.iliasdownloader.service.model.IliasItem
import com.github.thetric.iliasdownloader.service.webparser.impl.IliasItemIdStringParsingException
import org.jsoup.nodes.Element
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

private const val ROW_SEPARATOR = "  "

class IliasItemParserImpl(
    private val courseWebDavPrefix: String,
    private val courseLinkPrefix: String
) : IliasItemParser {
    override fun parseCourse(courseElement: Element): Course {
        val courseId = getCourseId(courseElement)
        val courseName = courseElement.text()
        val courseUrl = "$courseWebDavPrefix$courseId/"
        return Course(courseId, courseUrl, courseName)
    }

    private fun getCourseId(aTag: Element): Long {
        val href = aTag.attr("href")
        // href="http://www.ilias.fh-dortmund.de/ilias/goto_ilias-fhdo_crs_\d+.html"
        val idString =
            href.replaceFirst(courseLinkPrefix, "")
                .replace(".html", "")
        // der Rest muss ein int sein
        return parseId(href, idString)
    }

    override fun isFolder(itemRow: String): Boolean {
        return itemRow[0] == '-'
    }

    override fun parseFolder(parent: IliasItem, itemRow: String): CourseFolder {
        val firstPosSeparator = itemRow.indexOf(ROW_SEPARATOR)
        val secondPosSeparator = itemRow.indexOf(
            ROW_SEPARATOR,
            firstPosSeparator + ROW_SEPARATOR.length
        )
        val parsedLink = parseLink(itemRow, secondPosSeparator)
        return CourseFolder(
            name = parsedLink.name!!,
            url = resolveItemLink(parent, parsedLink.url!!),
            parent = parent
        )
    }

    override fun parseFile(parent: IliasItem, itemRow: String): CourseFile {
        val firstPosSeparator = itemRow.indexOf(ROW_SEPARATOR)
        val secondPosSeparator = itemRow.indexOf(
            ROW_SEPARATOR,
            firstPosSeparator + ROW_SEPARATOR.length
        )

        val parsedLinkName = parseLink(itemRow, secondPosSeparator)
        return CourseFile(
            name = parsedLinkName.name!!,
            url = resolveItemLink(parent, parsedLinkName.url!!),
            parent = parent,
            modified = parseLastModified(
                itemRow,
                firstPosSeparator,
                secondPosSeparator
            ),
            size = parseFileSize(itemRow, firstPosSeparator)
        )
    }
}

private fun resolveItemLink(parent: IliasItem, relUrl: String): String {
    return "${parent.url}/$relUrl"
}

val sizeSeparatorRegex = ",".toRegex()

private fun parseFileSize(itemRow: String, firstPosSeparator: Int): Int {
    val rawSizeInBytes = itemRow.subSequence(0, firstPosSeparator - 1)
    val sanitizedSizeInBytes = rawSizeInBytes.replace(sizeSeparatorRegex, "")
    return sanitizedSizeInBytes.toInt()
}

private val courseLinkRegex =
    Pattern.compile("""<a href="(?<url>.+)">(?<name>.+)</a>""")

private fun parseLink(
    itemRow: String,
    secondPosSeparator: Int
): ParsedIliasTableRow {
    val startIndex = secondPosSeparator + ROW_SEPARATOR.length
    val matcher =
        courseLinkRegex.matcher(itemRow.subSequence(startIndex, itemRow.length))
    if (!matcher.matches()) {
        throw IllegalStateException("Failed to parse $itemRow")
    }
    return ParsedIliasTableRow(matcher.group("name"), matcher.group("url"))
}

private val lastModifiedFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

private fun parseLastModified(
    itemRow: String,
    firstPosSeparator: Int,
    secondPosSep: Int
): LocalDateTime {
    val startIndex = firstPosSeparator + ROW_SEPARATOR.length
    val lastModifiedString = itemRow.substring(startIndex, secondPosSep)
    return LocalDateTime.parse(lastModifiedString, lastModifiedFormatter)
}

private fun parseId(href: String, probableIdString: String): Long {
    try {
        return probableIdString.toLong()
    } catch (e: NumberFormatException) {
        val msg =
            "Failed to parse \'$probableIdString\', original string was \'$href\'"
        throw IliasItemIdStringParsingException(msg, e)
    }
}
