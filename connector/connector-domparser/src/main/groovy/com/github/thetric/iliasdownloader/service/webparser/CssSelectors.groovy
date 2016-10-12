package com.github.thetric.iliasdownloader.service.webparser

import groovy.transform.CompileStatic

/**
 * @author broj
 * @since 18.08.2016
 */
@CompileStatic
enum CssSelectors {
    COURSE_SELECTOR("a[href*='_crs_'].il_ContainerItemTitle"),
    ITEM_CONTAINER_SELECTOR('.il_ContainerListItem'),
    ITEM_TITLE_SELECTOR('a.il_ContainerItemTitle'),
    ITEM_PROPERTIES_SELECTOR('.il_ItemProperty');

    String cssSelector

    CssSelectors(String cssSelector) {
        this.cssSelector = cssSelector
    }
}
