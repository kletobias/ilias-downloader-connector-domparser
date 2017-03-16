package com.github.thetric.iliasdownloader.ui.common.prefs

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

import java.nio.charset.StandardCharsets
import java.nio.file.Path

final class JsonUserPreferenceService implements UserPreferenceService {
    private final JsonSlurper jsonSlurper
    final Path settingsFile

    JsonUserPreferenceService(Path settingsPath) {
        this.settingsFile = settingsPath
        jsonSlurper = new JsonSlurper()
    }

    @Override
    UserPreferences loadUserPreferences() throws IOException {
        return settingsFile.withInputStream {
            def a = new UserPreferences(jsonSlurper.parse(it) as Map)
            return a
        } as UserPreferences
    }

    @Override
    void saveUserPreferences(final UserPreferences userPreferences) throws IOException {
        settingsFile.withWriter(StandardCharsets.UTF_8.name(), {
            def compressedJson = JsonOutput.toJson(userPreferences)
            def prettyPrintedJson = JsonOutput.prettyPrint(compressedJson)
            it.write("$prettyPrintedJson\n")
        })
    }
}