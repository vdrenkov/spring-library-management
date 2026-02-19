package dev.vdrenkov.slm.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String USER_NOT_FOUND_MESSAGE = "No such user found in the database";

    @Override
    public String getMessage() {
        return USER_NOT_FOUND_MESSAGE;
    }
}
