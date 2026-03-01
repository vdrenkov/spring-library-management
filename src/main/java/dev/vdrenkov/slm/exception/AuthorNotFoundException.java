package dev.vdrenkov.slm.exception;

/**
 * AuthorNotFoundException component.
 */
public class AuthorNotFoundException extends RuntimeException {

    private static final String AUTHOR_NOT_FOUND_MESSAGE = "No such author found in the database";

    @Override
    /**
     * Handles getMessage operation.
     * @return Exception message.
     */
    public String getMessage() {
        return AUTHOR_NOT_FOUND_MESSAGE;
    }
}
