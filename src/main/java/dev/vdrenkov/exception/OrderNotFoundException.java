package dev.vdrenkov.exception;

public class OrderNotFoundException extends RuntimeException {

  private static final String ORDER_NOT_FOUND_MESSAGE = "No such order was found in the database";

  @Override
  public String getMessage() {
    return ORDER_NOT_FOUND_MESSAGE;
  }
}

