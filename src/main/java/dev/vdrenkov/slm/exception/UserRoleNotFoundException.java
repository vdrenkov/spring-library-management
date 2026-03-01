package dev.vdrenkov.slm.exception;

/**
 * UserRoleNotFoundException component.
 */
public class UserRoleNotFoundException extends RuntimeException {

    private static final String ROLE_NOT_FOUND_MESSAGE = "No such role found in the database";

    @Override
    /**
     * Handles getMessage operation.
     * @return Exception message.
     */
    public String getMessage() {
        return ROLE_NOT_FOUND_MESSAGE;
    }
}
