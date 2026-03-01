package dev.vdrenkov.slm.exception;

/**
 * UserNotFoundException component.
 */
public class UserNotFoundException extends RuntimeException {

    private static final String USER_NOT_FOUND_MESSAGE = "No such user found in the database";

    @Override
    /**
     * Handles getMessage operation.
     * @return Exception message.
     */
    public String getMessage() {
        return USER_NOT_FOUND_MESSAGE;
    }
}
