package cse.dit012.lost.service;

import java9.util.concurrent.CompletableFuture;

/**
 * A service for logging in a user.
 */
public interface LoginService {
    /**
     * Tries to sign in the user.
     *
     * @return a future completing if the login succeeded and completes exceptionally if it failed
     */
    CompletableFuture<Void> login();
}
