package bg.vdrenkov.exception;

public class BookNotFoundException extends RuntimeException {

  private final static String BOOK_NOT_FOUND_MESSAGE = "No such book was found in the database";

  @Override
  public String getMessage() {
    return BOOK_NOT_FOUND_MESSAGE;
  }
}

