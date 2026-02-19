package bg.vdrenkov.exception;

public class ClientNotFoundException extends RuntimeException {

  private static final String CLIENT_NOT_FOUND_MESSAGE = "No such client was found in the database";

  @Override
  public String getMessage() {
    return CLIENT_NOT_FOUND_MESSAGE;
  }
}

