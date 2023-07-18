package bg.vdrenkov.util;

import lombok.Generated;

import java.time.LocalDate;

public final class Constants {

  public static final int ZERO = 0;
  public static final int ONE = 1;
  public static final int ID = 1;
  public static final int CHOICE = 3;
  public static final int PERIOD = 5;
  public static final int QUANTITY = 10;
  public static final int YEAR = 2000;
  public static final int MONTH = 1;
  public static final int DAY = 1;
  public static final long JWT_TOKEN_VALIDITY = 60 * 60;
  public static final String CHOICE_STRING = "3";
  public static final String PERIOD_STRING = "5";
  public static final LocalDate NOW = LocalDate.now();

  public static final LocalDate LOCAL_DATE = LocalDate.of(2000, 1, 1);
  public static final String DATE_STRING = LOCAL_DATE.toString();
  public static final String NAME = "Name";
  public static final String SURNAME = "Surname";
  public static final String PHONE_NUMBER = "0888888888";
  public static final String EMAIL = "ab@abv.bg";
  public static final String BOOKS_NAMES_LIST_VALUE = "Test";
  public static final String JWT_COOKIE_NAME = "Cookie";

  @Generated
  private Constants() {
    throw new IllegalStateException();
  }
}
