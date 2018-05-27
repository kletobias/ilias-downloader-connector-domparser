package com.github.thetric.iliasdownloader.service.webparser.impl.course

import com.github.thetric.iliasdownloader.service.model.Course
import com.github.thetric.iliasdownloader.service.model.CourseFile
import com.github.thetric.iliasdownloader.service.model.CourseFolder
import org.jsoup.nodes.Element

interface IliasItemParser {
    fun parseCourse(courseElement: Element): Course
    fun isFolder(itemRow: String): Boolean
    fun parseFolder(currentUrl: String, itemRow: String): CourseFolder
    fun parseFile(currentUrl: String, itemRow: String): CourseFile
}
