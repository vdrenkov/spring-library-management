package bg.vdrenkov.exception;

public class UserRoleNotFoundException extends RuntimeException {

  private static final String ROLE_NOT_FOUND_MESSAGE = "No such role was found in the database";

  @Override
  public String getMessage() {
    return ROLE_NOT_FOUND_MESSAGE;
  }
}
