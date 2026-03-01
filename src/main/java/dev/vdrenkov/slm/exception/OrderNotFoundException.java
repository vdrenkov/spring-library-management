package dev.vdrenkov.slm.exception;

/**
 * OrderNotFoundException component.
 */
public class OrderNotFoundException extends RuntimeException {

    private static final String ORDER_NOT_FOUND_MESSAGE = "No such order found in the database";

    @Override
    /**
     * Handles getMessage operation.
     * @return Exception message.
     */
    public String getMessage() {
        return ORDER_NOT_FOUND_MESSAGE;
    }
}
