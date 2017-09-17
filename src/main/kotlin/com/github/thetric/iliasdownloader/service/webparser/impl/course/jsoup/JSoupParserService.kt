package com.github.thetric.iliasdownloader.service.webparser.impl.course.jsoup

import org.jsoup.nodes.Document

/**
 * Abstraction for Jsoup's HTML parsing.
 */
interface JSoupParserService {
    fun parse(html: String): Document
}
