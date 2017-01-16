package com.github.thetric.iliasdownloader.cli

import com.github.thetric.iliasdownloader.service.IliasService
import com.github.thetric.iliasdownloader.service.SyncingIliasItemVisitor
import com.github.thetric.iliasdownloader.service.model.Course
import com.github.thetric.iliasdownloader.service.model.LoginCredentials
import groovy.transform.TupleConstructor
import groovy.util.logging.Log4j2
import io.reactivex.functions.Consumer

import java.nio.file.Path
import java.util.function.Function
/**
 * @author broj
 * @since 16.01.2017
 */
@Log4j2
@TupleConstructor
final class IliasCliController {
    private final Path basePath
    private final ConsoleService consoleService
    private final Function<String, IliasService> iliasProvider

    IliasCliController(Path basePath, ConsoleService consoleService, Function<String, IliasService> iliasProvider) {
        this.basePath = basePath
        this.consoleService = consoleService
        this.iliasProvider = iliasProvider
    }

    def start() {
        IliasService iliasService = createIliasService()
        log.info('Connected!')
        login(iliasService)
        log.info('Login succeeded')

        println ''
        println 'Available courses:'
        Collection<Course> joinedCourses = iliasService.joinedCourses

        log.info('Starting sync')
        def itemVisitor = new SyncingIliasItemVisitor(basePath, iliasService)
        iliasService.searchCoursesWithContent(joinedCourses)
                    .subscribe({ itemVisitor.visit(it) } as Consumer)
        log.info('Sync finished')
    }

    private IliasService createIliasService() {
        while (true) {
            String serverUrl = promptForServerUrl()
            try {
                return iliasProvider.apply(serverUrl)
            } catch (Exception e) {
                log.catching(e)
            }
        }
    }

    private promptForServerUrl() {
        return consoleService.readLine('ilias.server.url', 'Ilias Server URL')
    }

    private login(IliasService iliasService) {
        log.info('Prompting for credentials')

        def username = consoleService.readLine('ilias.credentials.username', 'Username: ')
        def password = consoleService.readPassword('ilias.credentials.password', 'Password: ')

        iliasService.login(new LoginCredentials(username, password))
    }
}
