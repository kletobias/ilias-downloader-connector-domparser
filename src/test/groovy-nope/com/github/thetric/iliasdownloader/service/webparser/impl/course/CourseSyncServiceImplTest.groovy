package com.github.thetric.iliasdownloader.service.webparser.impl.course

import com.github.thetric.iliasdownloader.service.webparser.impl.course.jsoup.JSoupParserService
import com.github.thetric.iliasdownloader.service.webparser.impl.webclient.IliasWebClient
import org.jsoup.nodes.Document
import spock.lang.Specification

class CourseSyncServiceImplTest extends Specification {
    private final JSoupParserService jSoupParserService = Mock()
    private final IliasWebClient webClient = Mock()
    private final IliasItemParser itemParser = Mock()

    private final String iliasBaseUrl = 'https://www.ilias.fh-dortmund.de/ilias/'
    private final courseOverview = "${iliasBaseUrl}ilias.php?baseClass=ilPersonalDesktopGUI&cmd=jumpToSelectedItems"

    private final CourseSyncServiceImpl sut = new CourseSyncServiceImpl(
        jSoupParserService,
        webClient,
        itemParser,
        courseOverview)

    def "getJoinedCourses: returns empty list if no courses can be found"() {
        setup:
        final String html = 'the html from the course overview'
        final Document doc = Stub(constructorArgs: [iliasBaseUrl])

        when:
        final def actual = sut.joinedCourses

        then:
        1 * webClient.getHtml(courseOverview) >> html
        1 * jSoupParserService.parse(html) >> doc
        doc.select(_) >> []

        actual == []
    }
}
