package com.github.thetric.iliasdownloader.ui.jfx.ui.util

import groovy.transform.CompileStatic
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.TextArea
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * @author broj
 * @since 25.09.2016
 */
@CompileStatic
final class DialogHelper {
    private static final Logger log = LogManager.logger

    private DialogHelper() {
        // prevent instantiation
        throw new IllegalAccessError()
    }

    static Optional<ButtonType> showExceptionDialog(String message, Throwable throwable) {
        def alert = new Alert(Alert.AlertType.ERROR)
        alert.title = 'Ilias Downloader 3 - Fehler'
        alert.headerText = message
        alert.contentText = throwable.localizedMessage

        TextArea exceptionLogTextArea = new TextArea(getPrintableString(throwable))
        exceptionLogTextArea.style = '-fx-font-family: Hack, Consolas, "Courier New", monospace'
        alert.dialogPane.expandableContent = exceptionLogTextArea

        return alert.showAndWait()
    }

    private static String getPrintableString(Throwable throwable) {
        new StringWriter().withPrintWriter {
            try {
                throwable.printStackTrace it
                return it.toString()
            } catch (IOException e) {
                // can't happen
                log.error('Could not close the StringWriter', e)
                return '<Fehler beim Erzeugen des Exception Stacktraces>'
            }
        }
    }
}
