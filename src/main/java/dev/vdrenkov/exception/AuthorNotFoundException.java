package dev.vdrenkov.exception;

public class AuthorNotFoundException extends RuntimeException {

  private final static String AUTHOR_NOT_FOUND_MESSAGE = "No such author was found in the database";

  @Override
  public String getMessage() {
    return AUTHOR_NOT_FOUND_MESSAGE;
  }
}

