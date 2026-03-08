package dev.vdrenkov.biblium.exception;

import java.io.Serial;

/**
 * UserNotFoundException component.
 */
public class UserNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1439534888642404581L;
    private static final String USER_NOT_FOUND_MESSAGE = "No such user found in the database";

    /**
     * Handles getMessage operation.
     *
     * @return Exception message.
     */
    @Override
    public String getMessage() {
        return USER_NOT_FOUND_MESSAGE;
    }
}
