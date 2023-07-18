package bg.vdrenkov.exception;

public class UserNotFoundException extends RuntimeException {

  private static final String USER_NOT_FOUND_MESSAGE = "No such user was found in the database";

  @Override
  public String getMessage() {
    return USER_NOT_FOUND_MESSAGE;
  }
}