package com.github.thetric.iliasdownloader.service.webparser.impl.webclient

import com.github.thetric.iliasdownloader.service.exception.IliasAuthenticationException
import com.github.thetric.iliasdownloader.service.exception.IliasException
import com.github.thetric.iliasdownloader.service.model.LoginCredentials
import mu.KotlinLogging
import okhttp3.FormBody
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.InputStream
import java.net.CookieManager
import java.net.CookiePolicy

/**
 * [IliasWebClient] communicating with the Ilias with OkHttp.
 */
class OkHttpIliasWebClient(
    iliasBaseUrl: String
) : IliasWebClient {
    private val client: OkHttpClient
    private val cookieManager: CookieManager = CookieManager()
    private val loginPage: String = "${iliasBaseUrl}login.php"
    private val logoutPage: String = "${iliasBaseUrl}logout.php"
    private val log = KotlinLogging.logger {}

    init {
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        client = OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(cookieManager))
            .followRedirects(false)
            .build()
    }

    override fun login(credentials: LoginCredentials) {
        log.info { "Logging in at $loginPage" }
        val loginForm = FormBody.Builder()
            .add("username", credentials.userName)
            .add("password", credentials.password)
            .build()
        val request = Request.Builder()
            .url(loginPage)
            .post(loginForm)
            .build()
        val loginClient = client.newBuilder()
            .followRedirects(true)
            .build()
        val response = loginClient.newCall(request).execute()
        checkResponse(loginPage, response)
        if (response.request().url().toString().startsWith(loginPage)) {
            clearCookies()
            throw IliasAuthenticationException("Login at $loginPage failed. Invalid credentials")
        }
        log.info { "Login at $loginPage succeeded " }
    }

    override fun logout() {
        log.info { "Logging out: $logoutPage" }
        val response = executeGetRequest(logoutPage)
        clearCookies()
        checkResponse(logoutPage, response)
        log.info { "Logout at ${logoutPage}succeeded" }
    }

    private fun clearCookies() {
        cookieManager.cookieStore.removeAll()
    }

    private fun executeGetRequest(url: String): Response {
        val request = Request.Builder().url(url).build()
        return client.newCall(request).execute()
    }

    override fun getHtml(url: String): String {
        val response = executeGetRequest(url)
        checkResponse(url, response)
        return response.body()!!.string()
    }

    override fun getAsInputStream(url: String): InputStream {
        val response = executeGetRequest(url)
        checkResponse(url, response)
        return response.body()!!.byteStream()
    }

    private fun checkResponse(url: String, response: Response) {
        if (!response.isSuccessful) {
            throw IliasException("Failed to GET $url: ${response.message()}")
        }
    }
}
