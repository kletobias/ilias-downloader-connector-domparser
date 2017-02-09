package com.github.thetric.iliasdownloader.service.webparser.impl.util

import com.github.thetric.iliasdownloader.service.exception.IliasException
import groovy.transform.CompileStatic

/**
 * @author broj
 * @since 31.05.2016
 */
@CompileStatic
interface WebIoExceptionTranslator {
    IliasException translate(Exception e)
}