package com.github.thetric.iliasdownloader.service.webparser

import com.github.thetric.iliasdownloader.service.IliasService
import com.github.thetric.iliasdownloader.service.IliasServiceProvider
import com.github.thetric.iliasdownloader.service.webparser.impl.CookieNotFoundException
import com.github.thetric.iliasdownloader.service.webparser.impl.WebIliasService
import com.github.thetric.iliasdownloader.service.webparser.impl.course.CourseSyncServiceImpl
import com.github.thetric.iliasdownloader.service.webparser.impl.course.jsoup.JSoupParserServiceImpl
import com.github.thetric.iliasdownloader.service.webparser.impl.webclient.OkHttpIliasWebClient
import java.io.IOException
import java.util.Optional

private const val LOGIN_PAGE_NAME = "login.php"
private const val ILIAS_CLIENT_ID_COOKIE_NAME = "ilClientId"

/**
 * Provides an [IliasService] that retrieves information from the Ilias service by parsing the HTML pages.
 */
class WebParserIliasServiceProvider @Throws(IOException::class)
constructor(
    private val cookieService: CookieService,
    loginPage: String
) : IliasServiceProvider {
    val iliasBaseUrl: String
    val clientId: String

    init {
        iliasBaseUrl = retrieveBaseUrl(loginPage)
        clientId = retrieveClientId(loginPage)
    }

    private fun retrieveBaseUrl(loginPage: String): String {
        var trimmed = loginPage.trim { it <= ' ' }
        if (trimmed.isEmpty()) {
            throw IllegalArgumentException("Die angegebene Loginseiten URL darf nicht leer sein")
        }

        if (!trimmed.startsWith("http://") && !trimmed.startsWith("https://")) {
            trimmed = "https://$loginPage"
        }

        val loginPageNameIndex = trimmed.indexOf(LOGIN_PAGE_NAME)
        if (loginPageNameIndex == -1) {
            throw IllegalArgumentException("Die angegebene URL enthält kein \'$LOGIN_PAGE_NAME\'")
        }

        return trimmed.substring(0, loginPageNameIndex)
    }

    @Throws(IOException::class)
    private fun retrieveClientId(loginPage: String): String {
        val id: String
        try {
            id = cookieService.getCookieFromUrl(loginPage, ILIAS_CLIENT_ID_COOKIE_NAME)
        } catch (e: IOException) {
            throw IOException("Konnte die URL \'$loginPage\' nicht erreichen", e)
        }

        return Optional.ofNullable(id)
            .orElseThrow {
                CookieNotFoundException("Konnte das Cookie '$ILIAS_CLIENT_ID_COOKIE_NAME\' nicht in der Response von der Seite " +
                    "$loginPage finden")
            }
    }

    override fun newInstance(): IliasService {
        val jSoupParserService = JSoupParserServiceImpl()
        val iliasWebClient = OkHttpIliasWebClient(iliasBaseUrl)
        val courseSyncServiceProvider = CourseSyncServiceImpl(
            jSoupParserService,
            iliasWebClient,
            iliasBaseUrl,
            clientId)
        return WebIliasService(courseSyncServiceProvider, iliasWebClient)
    }
}
