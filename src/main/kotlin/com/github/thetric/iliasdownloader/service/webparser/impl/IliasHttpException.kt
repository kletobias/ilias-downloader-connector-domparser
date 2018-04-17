package com.github.thetric.iliasdownloader.service.webparser.impl

import com.github.thetric.iliasdownloader.service.exception.IliasException

class IliasHttpException(
    override val message: String,
    val url: String,
    val statusCode: Int
) : IliasException(message)
