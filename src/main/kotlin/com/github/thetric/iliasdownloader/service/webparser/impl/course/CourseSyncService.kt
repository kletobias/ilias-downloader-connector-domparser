package com.github.thetric.iliasdownloader.service.webparser.impl.course

import com.github.thetric.iliasdownloader.service.ContextAwareIliasItemVisitor
import com.github.thetric.iliasdownloader.service.model.Course
import com.github.thetric.iliasdownloader.service.model.IliasItem

/**
 * Provides methods for access to Ilias courses.
 */
interface CourseSyncService {
    val joinedCourses: Collection<Course>

    fun <C> visit(
        parentContext: C,
        courseItem: IliasItem,
        itemVisitor: ContextAwareIliasItemVisitor<C>
    )
}
