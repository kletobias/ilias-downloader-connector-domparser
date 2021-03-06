<a name="5.0.0"></a>
# [5.0.0](https://github.com/thetric/ilias-downloader-connector-domparser/compare/5.0.0-beta.2...5.0.0) (2018-05-27)


<a name="5.0.0-beta.3"></a>
# [5.0.0-beta.3](https://github.com/thetric/ilias-downloader-connector-domparser/compare/5.0.0-beta.2...5.0.0-beta.3) (2018-05-27)


### Bug Fixes

* **sync:** don't attempt to parse a course as a file ([a77cad8](https://github.com/thetric/ilias-downloader-connector-domparser/commit/a77cad8))



<a name="5.0.0-beta.2"></a>
# [5.0.0-beta.2](https://github.com/thetric/ilias-downloader-connector-domparser/compare/5.0.0-beta.1...5.0.0-beta.2) (2018-05-27)


### Bug Fixes

* **sync:** pass context to a course's child items ([8544a55](https://github.com/thetric/ilias-downloader-connector-domparser/commit/8544a55))



<a name="5.0.0-beta.1"></a>
# [5.0.0-beta.1](https://github.com/thetric/ilias-downloader-connector-domparser/compare/5.0.0-beta.0...5.0.0-beta.1) (2018-05-27)


### Bug Fixes

* use correct jdk for publishing via jitpack ([f75e27b](https://github.com/thetric/ilias-downloader-connector-domparser/commit/f75e27b))



<a name="5.0.0-beta.0"></a>
# [5.0.0-beta.0](https://github.com/thetric/ilias-downloader-connector-domparser/compare/4.2.2...5.0.0-beta.0) (2018-05-27)


### Code Refactoring

* upgrade to connector-api 6 ([7e2d038](https://github.com/thetric/ilias-downloader-connector-domparser/commit/7e2d038))


### BREAKING CHANGES

* upgraded to connector-api 6



<a name="4.2.2"></a>
## [4.2.2](https://github.com/thetric/ilias-downloader-connector-domparser/compare/4.2.1...4.2.2) (2018-04-22)


### Bug Fixes

* don't ignore the last digit of the file size ([79a5a19](https://github.com/thetric/ilias-downloader-connector-domparser/commit/79a5a19))



<a name="4.2.1"></a>
## [4.2.1](https://github.com/thetric/ilias-downloader-connector-domparser/compare/4.2.0...4.2.1) (2018-04-22)


### Bug Fixes

* correctly assign Course.url and .name ([2fbb9ee](https://github.com/thetric/ilias-downloader-connector-domparser/commit/2fbb9ee))



<a name="4.2.0"></a>
# [4.2.0](https://github.com/thetric/ilias-downloader-connector-domparser/compare/4.1.1...4.2.0) (2018-04-18)


### Features

* allow dynamic client ids for the webdav url ([9a297ed](https://github.com/thetric/ilias-downloader-connector-domparser/commit/9a297ed)), closes [#2](https://github.com/thetric/ilias-downloader-connector-domparser/issues/2)


### Performance Improvements

* **item-parser:** re-use file size separator regex ([c66d21e](https://github.com/thetric/ilias-downloader-connector-domparser/commit/c66d21e))



<a name="4.1.1"></a>
## [4.1.1](https://github.com/thetric/ilias-downloader-connector-domparser/compare/4.0.10...4.1.1) (2018-04-17)

**IMPORTANT UPDATE** for releases from 4.0.8 to 4.1.0! The Maven POMs generated
by JitPack didn't include the required dependencies, this was fixed now.

### Bug Fixes

* correct gradle dependency scopes ([111c44d](https://github.com/thetric/ilias-downloader-connector-domparser/commit/111c44d))

<a name="4.1.0"></a>
# [4.1.0](https://github.com/thetric/ilias-downloader-connector-domparser/compare/4.0.10...4.1.0) (2018-04-17)


### Features

* add url and status code to the exception if the response is not successful ([0857c47](https://github.com/thetric/ilias-downloader-connector-domparser/commit/0857c47))



<a name="4.0.10"></a>
## [4.0.10](https://github.com/thetric/ilias-downloader-connector-domparser/compare/4.0.9...4.0.10) (2018-04-16)

Correct Kotlin dependency: use `kotlin-stdlib-jdk8` instead of deprecated
`kotlin-stdlib-jre8`.

<a name="4.0.9"></a>
## [4.0.9](https://github.com/thetric/ilias-downloader-connector-domparser/compare/4.0.8...4.0.9) (2018-02-21)

Minor performance improvements due to dependency updates (Kotlin).

<a name="4.0.8"></a>
## [4.0.8](https://github.com/thetric/ilias-downloader-connector-domparser/compare/4.0.7...4.0.8) (2017-11-19)

Maintenance release (updated deps, cleaned up Maven POM)

<a name="4.0.7"></a>
## [4.0.7](https://github.com/thetric/ilias-downloader-connector-domparser/compare/4.0.6...4.0.7) (2017-11-08)

Housekeeping (dependency updates, refactoring)

<a name="4.0.6"></a>
## [4.0.6](https://github.com/thetric/ilias-downloader-connector-domparser/compare/4.0.5...4.0.6) (2017-09-28)


### Bug Fixes

* close login response ([8502bde](https://github.com/thetric/ilias-downloader-connector-domparser/commit/8502bde))



<a name="4.0.5"></a>
## [4.0.5](https://github.com/thetric/ilias-downloader-connector-domparser/compare/4.0.4...4.0.5) (2017-09-19)

Use correct scope of slf4j-api


<a name="4.0.4"></a>
## [4.0.4](https://github.com/thetric/ilias-downloader-connector-domparser/compare/4.0.3...4.0.4) (2017-09-19)

Migrated from log4j2 to SLF4J+logback.

<a name="4.0.3"></a>
## [4.0.3](https://github.com/thetric/ilias-downloader-connector-domparser/compare/4.0.2...4.0.3) (2017-09-19)


### Bug Fixes

* correctly split the last modified date from the plain text ([411af4a](https://github.com/thetric/ilias-downloader-connector-domparser/commit/411af4a))



<a name="4.0.2"></a>
## [4.0.2](https://github.com/thetric/ilias-downloader-connector-domparser/compare/4.0.0...4.0.2) (2017-09-19)


### Bug Fixes

* correctly split the item row entry ([33b24f4](https://github.com/thetric/ilias-downloader-connector-domparser/commit/33b24f4))



<a name="4.0.1"></a>
## [4.0.1](https://github.com/thetric/ilias-downloader-connector-domparser/compare/4.0.0...4.0.1) (2017-09-18)

dependency updates, minor refactorings


<a name="4.0.0"></a>
# [4.0.0](https://github.com/thetric/ilias-downloader-connector-domparser/compare/3.0.0...4.0.0) (2017-09-17)

**BREAKING CHANGE:** Migration from Groovy to Kotlin.

<a name="3.0.0"></a>
# [3.0.0](https://github.com/thetric/ilias-downloader-connector-domparser/compare/2.0.2...v3.0.0) (2017-06-24)

### BREAKING CHANGES

* The Maven coordinates in JitPack have changed to `com.github.thetric`  (group ID) and `ilias-downloader-connector-domparser` (artifact ID)

<a name="2.0.2"></a>
## [2.0.2](https://github.com/thetric/ilias-downloader-connector-domparser/compare/2.0.1...v2.0.2) (2017-06-17)


### Bug Fixes

* **ui/cli:** don't print a groovy runtime exception due to wrong API ([59f5630](https://github.com/thetric/ilias-downloader-connector-domparser/commit/59f5630))



<a name="2.0.1"></a>
## [2.0.1](https://github.com/thetric/ilias-downloader-connector-domparser/compare/2.0.0...v2.0.1) (2017-06-13)

Fix `Error: Could not find or load main class com.github.thetric.iliasdownloader.cli.Cli`

### Bug Fixes

* **ui/cli:** close scanner after usage ([5082a86](https://github.com/thetric/ilias-downloader-connector-domparser/commit/5082a86))



<a name="2.0.0"></a>
# [2.0.0](https://github.com/thetric/ilias-downloader-connector-domparser/compare/1.1.0...v2.0.0) (2017-06-10)

This release contains a breaking change for developers using the `connector` modules.

One notable feature for the CLI users is that the sync log shows the downloaded files (with `file://` URL).

 Another change is the distribution of the CLI as ZIP instead of a JAR.
 The CLI can be launched by unzipping the archive and executing the corresponding shell script (`bin/cli` for Linux/Mac, `bin/cli.bat` for Windows).

### Bug Fixes

* **cli:** limit cache size of paths ([c80e00d](https://github.com/thetric/ilias-downloader-connector-domparser/commit/c80e00d))


### Code Refactoring

* **connector/api:** use interface instead of closure as Visitor ([19bb82d](https://github.com/thetric/ilias-downloader-connector-domparser/commit/19bb82d))


### Features

* **ui/cli:** print URL for downloaded files ([e8777fe](https://github.com/thetric/ilias-downloader-connector-domparser/commit/e8777fe))


### BREAKING CHANGES

* **connector/api:** The **signature of IliasService#visit has changed** from `Course, Closure<VisitResult>` to `Course, IliasItemVisitor`.
For Groovy devs nothing changes (due to java 8's default interface impls), Java devs must accommodate to the new API.



<a name="1.1.0"></a>
# [1.1.0](https://github.com/thetric/ilias-downloader-connector-domparser/compare/1.0.2...1.1.0) (2017-04-09)


### Bug Fixes

* **cli:** handle login err and do not crash ([6b2a4f2](https://github.com/thetric/ilias-downloader-connector-domparser/commit/6b2a4f2))
* **cli:** print "login successful" msg only if login succeeded ([c4e50cc](https://github.com/thetric/ilias-downloader-connector-domparser/commit/c4e50cc))
* **cli:** show course selection if no courses are active ([aa6135e](https://github.com/thetric/ilias-downloader-connector-domparser/commit/aa6135e))
* **cli:** update activeCourse ids only one + make them unique ([7a23df2](https://github.com/thetric/ilias-downloader-connector-domparser/commit/7a23df2))
* **common-ui:** create parent dirs for settings file if parent don't exist ([9f51f0f](https://github.com/thetric/ilias-downloader-connector-domparser/commit/9f51f0f))
* **connector-domparser:** check if login has succeeded ([831b19b](https://github.com/thetric/ilias-downloader-connector-domparser/commit/831b19b))
* **connector-domparser:** correctly prepend 'https://' ([29e1a9b](https://github.com/thetric/ilias-downloader-connector-domparser/commit/29e1a9b))
* **connector-domparser:** extract base url from trimmed url string ([b68372e](https://github.com/thetric/ilias-downloader-connector-domparser/commit/b68372e))
* **connector-domparser:** try to preserve content's original encoding otherwise use iso 8859-1 ([8b3cfb5](https://github.com/thetric/ilias-downloader-connector-domparser/commit/8b3cfb5))


### Features

* **cli:** default to all courses if the input is blank when prompting the user for the courses to sync ([fe3b061](https://github.com/thetric/ilias-downloader-connector-domparser/commit/fe3b061)), closes [#7](https://github.com/thetric/ilias-downloader-connector-domparser/issues/7)
* **cli:** include username in password prompt ([28bb0a2](https://github.com/thetric/ilias-downloader-connector-domparser/commit/28bb0a2))
* **connector-domparser:** add okHttp web client impl ([3e27d9c](https://github.com/thetric/ilias-downloader-connector-domparser/commit/3e27d9c))
* **ui-common:** add UserPrefSrvc.getSettingsFile ([4e2ce9e](https://github.com/thetric/ilias-downloader-connector-domparser/commit/4e2ce9e))



<a name="1.0.2"></a>
## [1.0.2](https://github.com/thetric/ilias-downloader-connector-domparser/compare/1.0.1...1.0.2) (2017-04-03)


### Bug Fixes

* do not reset maxFileSize to 0 ([8d60bac](https://github.com/thetric/ilias-downloader-connector-domparser/commit/8d60bac)), closes [#6](https://github.com/thetric/ilias-downloader-connector-domparser/issues/6)



<a name="1.0.1"></a>
## [1.0.1](https://github.com/thetric/ilias-downloader-connector-domparser/compare/1.0.0...1.0.1) (2017-04-03)


### Bug Fixes

* save selected courses ([a47f9e3](https://github.com/thetric/ilias-downloader-connector-domparser/commit/a47f9e3))
* use 0 instead of null for default file size ([e0bed4c](https://github.com/thetric/ilias-downloader-connector-domparser/commit/e0bed4c)), closes [#5](https://github.com/thetric/ilias-downloader-connector-domparser/issues/5)



<a name="1.0.0"></a>
# [1.0.0](https://github.com/thetric/ilias-downloader-connector-domparser/compare/313963c...1.0.0) (2017-03-16)


### Bug Fixes

* relative DateTime is now parsed with seconds+nanos set at 0 ([61d1c69](https://github.com/thetric/ilias-downloader-connector-domparser/commit/61d1c69))
* remove [@CompileStatic](https://github.com/CompileStatic) to make visitor pattern work ([313963c](https://github.com/thetric/ilias-downloader-connector-domparser/commit/313963c))
* replace existing files in sync ([b309f75](https://github.com/thetric/ilias-downloader-connector-domparser/commit/b309f75))
* set correct mod time of files ([78e68b0](https://github.com/thetric/ilias-downloader-connector-domparser/commit/78e68b0))
* use sync dir from cli args instead of hardcoded one ([7377768](https://github.com/thetric/ilias-downloader-connector-domparser/commit/7377768))
* **cli:** save prefs on 1st start ([cbdf153](https://github.com/thetric/ilias-downloader-connector-domparser/commit/cbdf153))
* **common:** create parent dirs of settings path only if not exist ([49557e2](https://github.com/thetric/ilias-downloader-connector-domparser/commit/49557e2))


### Features

* **cli:** accept download size limit ([0890231](https://github.com/thetric/ilias-downloader-connector-domparser/commit/0890231))
* **cli:** cache paths of parent ilias items while syncing ([15b25b1](https://github.com/thetric/ilias-downloader-connector-domparser/commit/15b25b1))
* **cli:** check file size limit in sync ([92abe32](https://github.com/thetric/ilias-downloader-connector-domparser/commit/92abe32))
* **cli:** enable course selection ([7f5558a](https://github.com/thetric/ilias-downloader-connector-domparser/commit/7f5558a)), closes [#3](https://github.com/thetric/ilias-downloader-connector-domparser/issues/3)
* **cli:** use json user pref srvc instead of yml ([231681d](https://github.com/thetric/ilias-downloader-connector-domparser/commit/231681d))
* **common:** add json user pref service ([0004194](https://github.com/thetric/ilias-downloader-connector-domparser/commit/0004194))
* **common:** create parent dirs + settings file if not exist ([1d25081](https://github.com/thetric/ilias-downloader-connector-domparser/commit/1d25081))
* **connector:** replace IliasService.getCourseItems(Course) with traversal method ([dab41ab](https://github.com/thetric/ilias-downloader-connector-domparser/commit/dab41ab))
* **connector-domparser:** extract file size ([5ac7592](https://github.com/thetric/ilias-downloader-connector-domparser/commit/5ac7592))
* **i18n:** add translation for 'download limit' cli option ([66edf6b](https://github.com/thetric/ilias-downloader-connector-domparser/commit/66edf6b))



