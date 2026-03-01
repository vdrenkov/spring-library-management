package dev.vdrenkov.slm.exception;

/**
 * BookNotFoundException component.
 */
public class BookNotFoundException extends RuntimeException {

    private static final String BOOK_NOT_FOUND_MESSAGE = "No such book found in the database";

    @Override
    /**
     * Handles getMessage operation.
     * @return Exception message.
     */
    public String getMessage() {
        return BOOK_NOT_FOUND_MESSAGE;
    }
}
