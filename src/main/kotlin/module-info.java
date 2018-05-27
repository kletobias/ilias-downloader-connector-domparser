module com.github.thetric.iliasdownloader.connector.domparser {
    requires java.base;
    requires kotlin.stdlib;

    requires ilias.downloader.connector.api;
    requires org.jsoup;
    requires kotlin.logging;
    requires okhttp3.urlconnection;

    exports com.github.thetric.iliasdownloader.service.webparser;
}
