package com.github.thetric.iliasdownloader.service.webparser

import java.io.IOException

/**
 * Retrieves a cookie from a request to the specified URL. The request contains **no authentication information**.
 */
interface CookieService {
    @Throws(IOException::class)
    fun getCookieFromUrl(url: String, cookieName: String): String
}
