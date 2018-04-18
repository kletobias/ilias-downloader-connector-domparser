package com.github.thetric.iliasdownloader.service.webparser.impl.course

import com.github.thetric.iliasdownloader.service.IliasItemVisitor
import com.github.thetric.iliasdownloader.service.model.Course
import com.github.thetric.iliasdownloader.service.model.IliasItem

/**
 * Provides methods for access to Ilias courses.
 */
interface CourseSyncService {
    val joinedCourses: Collection<Course>

    fun visit(
        courseItem: IliasItem,
        itemVisitor: IliasItemVisitor
    ): IliasItemVisitor.VisitResult
}
