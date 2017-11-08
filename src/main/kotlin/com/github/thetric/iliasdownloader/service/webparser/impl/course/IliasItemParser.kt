package com.github.thetric.iliasdownloader.service.webparser.impl.course

import com.github.thetric.iliasdownloader.service.model.Course
import com.github.thetric.iliasdownloader.service.model.CourseFile
import com.github.thetric.iliasdownloader.service.model.CourseFolder
import com.github.thetric.iliasdownloader.service.model.IliasItem
import org.jsoup.nodes.Element

interface IliasItemParser {
    fun parseCourse(courseElement: Element): Course
    fun isFolder(itemRow: String): Boolean
    fun parseFolder(parent: IliasItem, itemRow: String): CourseFolder
    fun parseFile(parent: IliasItem, itemRow: String): CourseFile
}
