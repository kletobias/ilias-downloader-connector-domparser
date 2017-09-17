package com.github.thetric.iliasdownloader.service.webparser.impl

import com.github.thetric.iliasdownloader.service.exception.IliasException

/**
 * Thrown if a specified cookie cannot be found in a response.
 */
class CookieNotFoundException(message: String) : IliasException(message)
