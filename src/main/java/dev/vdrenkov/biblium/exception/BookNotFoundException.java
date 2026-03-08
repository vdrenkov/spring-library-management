package dev.vdrenkov.biblium.exception;

import java.io.Serial;

/**
 * BookNotFoundException component.
 */
public class BookNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5911032288988321794L;
    private static final String BOOK_NOT_FOUND_MESSAGE = "No such book found in the database";

    /**
     * Handles getMessage operation.
     *
     * @return Exception message.
     */
    @Override
    public String getMessage() {
        return BOOK_NOT_FOUND_MESSAGE;
    }
}
