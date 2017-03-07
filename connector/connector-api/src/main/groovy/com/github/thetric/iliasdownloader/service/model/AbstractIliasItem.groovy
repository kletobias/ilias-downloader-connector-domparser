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
    final int id
    final String name
    final String url
    final IliasItem parent

    AbstractIliasItem(int id, String name, String url, IliasItem parent) {
        this.id = id
        this.name = name
        this.url = url
        this.parent = parent
    }
}
