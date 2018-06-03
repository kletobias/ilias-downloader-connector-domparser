package com.github.thetric.iliasdownloader.service.webparser

import com.github.thetric.iliasdownloader.service.webparser.impl.CookieNotFoundException
import com.nhaarman.mockito_kotlin.eq
import org.amshove.kluent.When
import org.amshove.kluent.`should throw`
import org.amshove.kluent.`with cause`
import org.amshove.kluent.any
import org.amshove.kluent.calling
import org.amshove.kluent.mock
import org.amshove.kluent.shouldThrow
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.mockito.stubbing.OngoingStubbing
import java.io.IOException

infix fun <T> OngoingStubbing<T>.itThrows(value: Exception): OngoingStubbing<T> = this.thenThrow(value)


object WebParserIliasServiceProviderSpec : Spek({
    describe("WebParserIliasServiceProvider") {
        describe("constructor") {

            it("constructor: throw err if login page contains only whitespaces") {
                val cookieService: CookieService = mock();
                {
                    WebParserIliasServiceProvider(cookieService, "   ")
                } shouldThrow IllegalArgumentException::class
            }

            it("rethrow IOEx from cookieService") {
                val cookieService: CookieService = mock()
                val loginUrl =
                    "https://www.ilias.fh-dortmund.de/ilias/login.php"
                When calling cookieService.getCookieFromUrl(
                    eq(loginUrl),
                    any()
                ) itThrows IOException("");
                {
                    WebParserIliasServiceProvider(cookieService, loginUrl)
                } shouldThrow IOException::class `with cause` IOException::class
            }

            it("throw NoCookiesAvailableException if cookie cannot be found") {
                val cookieService: CookieService = mock()
                val loginUrl =
                    "https://www.ilias.fh-dortmund.de/ilias/login.php"
                {
                    WebParserIliasServiceProvider(cookieService, loginUrl)
                } `should throw` CookieNotFoundException::class
            }
        }
    }
})
