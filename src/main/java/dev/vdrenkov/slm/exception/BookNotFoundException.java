package dev.vdrenkov.slm.exception;

public class BookNotFoundException extends RuntimeException {

    private static final String BOOK_NOT_FOUND_MESSAGE = "No such book found in the database";

    @Override
    public String getMessage() {
        return BOOK_NOT_FOUND_MESSAGE;
    }
}

