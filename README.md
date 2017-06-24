[![Travis](https://img.shields.io/travis/thetric/ilias-downloader-connector-domparser/master.svg?style=flat-square)](https://travis-ci.org/thetric/ilias-downloader-connector-domparser)
[![GitHub release](https://img.shields.io/github/release/thetric/ilias-downloader-connector-domparser.svg?style=flat-square)](https://github.com/thetric/ilias-downloader-connector-domparser/releases)

# Ilias Downloader - Connector (DOM parser)

Implementation of the [Ilias Downloader Connector API](https://github.com/thetric/ilias-downloader-connector-api) by accessing the web pages.

## Dependency

The JAR is available through [JitPack.io](https://jitpack.io/#thetric/ilias-downloader-connector-domparser) by selecting a release and a click on the 'Get it' button.
Instructions on how to add the dependency in your build tools can be found there, too.

## Usage

```groovy
final IliasServiceProvider iliasServiceProvider = new WebParserIliasServiceProvider(new JsoupCookieService(), ILIAS_SERVER_LOGIN_URL)
final IliasService iliasService = iliasServiceProvider.newInstance()
// use the iliasService
```

This library requires at least Java 8.
