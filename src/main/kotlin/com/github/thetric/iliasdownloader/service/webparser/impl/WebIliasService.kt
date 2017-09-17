package com.github.thetric.iliasdownloader.service.webparser.impl

import com.github.thetric.iliasdownloader.service.IliasItemVisitor
import com.github.thetric.iliasdownloader.service.IliasService
import com.github.thetric.iliasdownloader.service.model.Course
import com.github.thetric.iliasdownloader.service.model.CourseFile
import com.github.thetric.iliasdownloader.service.model.LoginCredentials
import com.github.thetric.iliasdownloader.service.webparser.impl.course.CourseSyncService
import com.github.thetric.iliasdownloader.service.webparser.impl.webclient.IliasWebClient

import java.io.InputStream

/**
 * [IliasService] parsing the HTML from the Ilias website.
 */
class WebIliasService(
    private val courseSyncService: CourseSyncService,
    private val iliasWebClient: IliasWebClient
) : IliasService {

    override fun getContentAsStream(file: CourseFile): InputStream {
        return iliasWebClient.getAsInputStream(file.url)
    }

    override fun login(loginCredentials: LoginCredentials) {
        iliasWebClient.login(loginCredentials)
    }

    override fun logout() {
        iliasWebClient.logout()
    }

    override fun getJoinedCourses(): Collection<Course> {
        return courseSyncService.joinedCourses
    }

    override fun visit(courseItem: Course, itemVisitor: IliasItemVisitor) {
        courseSyncService.visit(courseItem, itemVisitor)
    }
}
