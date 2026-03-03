package dev.vdrenkov.biblium.util;

import java.time.LocalDate;

/**
 * Constants component.
 */
public final class Constants {

    /**
     * Default identifier value used in tests.
     */
    public static final int ID = 1;
    /**
     * Default date-choice selector used in tests.
     */
    public static final int CHOICE = 3;
    /**
     * Default period value used in tests.
     */
    public static final int PERIOD = 5;
    /**
     * Default quantity value used in tests.
     */
    public static final int QUANTITY = 10;
    /**
     * Default year value used in tests.
     */
    public static final int YEAR = 2000;
    /**
     * Default month value used in tests.
     */
    public static final int MONTH = 1;
    /**
     * Default day value used in tests.
     */
    public static final int DAY = 1;
    /**
     * JWT token validity in seconds.
     */
    public static final long JWT_TOKEN_VALIDITY = 60L * 60L;
    /**
     * Default string representation of date-choice selector.
     */
    public static final String CHOICE_STRING = "3";
    /**
     * Default string representation of period value.
     */
    public static final String PERIOD_STRING = "5";
    /**
     * Default local date used in tests.
     */
    public static final LocalDate LOCAL_DATE = LocalDate.of(YEAR, MONTH, DAY);
    /**
     * Default date string used in tests.
     */
    public static final String DATE_STRING = LOCAL_DATE.toString();
    /**
     * Default first name used in tests.
     */
    public static final String NAME = "Name";
    /**
     * Default surname used in tests.
     */
    public static final String SURNAME = "Surname";
    /**
     * Default phone number used in tests.
     */
    public static final String PHONE_NUMBER = "0888888888";
    /**
     * Default email used in tests.
     */
    public static final String EMAIL = "ab@abv.bg";
    /**
     * Default book name value used in tests.
     */
    public static final String BOOKS_NAMES_LIST_VALUE = "Test";
    /**
     * JWT HTTP cookie name.
     */
    public static final String JWT_COOKIE_NAME = "BIBLIUM_AUTH";

    /**
     * Handles Constants operation.
     */
    private Constants() {
        throw new IllegalStateException("Utility class. Do not instantiate!");
    }
}
