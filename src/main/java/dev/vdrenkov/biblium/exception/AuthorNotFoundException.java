package dev.vdrenkov.biblium.exception;

import java.io.Serial;

/**
 * AuthorNotFoundException component.
 */
public class AuthorNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4546419479939047152L;
    private static final String AUTHOR_NOT_FOUND_MESSAGE = "No such author found in the database";

    /**
     * Handles getMessage operation.
     *
     * @return Exception message.
     */
    @Override
    public String getMessage() {
        return AUTHOR_NOT_FOUND_MESSAGE;
    }
}
