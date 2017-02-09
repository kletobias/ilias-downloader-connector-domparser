package com.github.thetric.iliasdownloader.service.webparser.impl

import groovy.transform.CompileStatic
import org.apache.http.client.config.CookieSpecs
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.fluent.Executor
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients

@CompileStatic
final class FluentHcExecutorProviderImpl implements FluentHcExecutorProvider {
    @Override
    Executor createFluentHcExecutor(org.apache.http.client.CookieStore cookieStore) {
        // reuse existing executor?!
        RequestConfig globalConfig = RequestConfig.custom()
                                                  .setCookieSpec(CookieSpecs.DEFAULT)
                                                  .build()
        CloseableHttpClient httpClient = HttpClients.custom()
                                                    .setDefaultRequestConfig(globalConfig)
                                                    .build()
        return Executor.newInstance(httpClient)
                       .use(cookieStore)
    }
}