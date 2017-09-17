package com.github.thetric.iliasdownloader.service.webparser.impl.webclient

import com.github.thetric.iliasdownloader.service.model.LoginCredentials

import java.io.InputStream

/**
 *
 */
interface IliasWebClient {
    fun getAsInputStream(url: String): InputStream

    fun login(credentials: LoginCredentials)

    fun logout()

    fun getHtml(url: String): String
}
