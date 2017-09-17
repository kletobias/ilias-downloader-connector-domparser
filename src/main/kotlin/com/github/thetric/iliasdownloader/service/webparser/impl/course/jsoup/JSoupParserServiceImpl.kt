package com.github.thetric.iliasdownloader.service.webparser.impl.course.jsoup

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 *
 */
class JSoupParserServiceImpl : JSoupParserService {
    override fun parse(html: String): Document {
        return Jsoup.parse(html)
    }
}
