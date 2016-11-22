package com.github.thetric.iliasdownloader.service.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor

/**
 * @author broj
 * @since 18.08.2016
 */
@TupleConstructor
@ToString(includeNames = true)
@EqualsAndHashCode
@CompileStatic
abstract class AbstractIliasItem implements IliasItem {
    int id
    String name
    String url

    AbstractIliasItem(int id, String name, String url) {
        this.id = id
        this.name = name
        this.url = url
    }
}
