package com.github.thetric.iliasdownloader.ui.jfx.sync

import com.github.thetric.iliasdownloader.service.IliasService
import javafx.concurrent.Service
import javafx.concurrent.Task
import lombok.AllArgsConstructor
import lombok.NonNull

/**
 * Service für den Ilias Logout.
 *
 * @author Dominik Broj
 * @since 01.02.2016
 */
@AllArgsConstructor
final class IliasLogoutService extends Service<Void> {
    @NonNull
    private final IliasService iliasSoapService;

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                iliasSoapService.logout();
                return null;
            }
        };
    }
}
