package dev.vdrenkov.slm.exception;

/**
 * ClientNotFoundException component.
 */
public class ClientNotFoundException extends RuntimeException {

    private static final String CLIENT_NOT_FOUND_MESSAGE = "No such client found in the database";

    @Override
    /**
     * Handles getMessage operation.
     * @return Exception message.
     */
    public String getMessage() {
        return CLIENT_NOT_FOUND_MESSAGE;
    }
}
