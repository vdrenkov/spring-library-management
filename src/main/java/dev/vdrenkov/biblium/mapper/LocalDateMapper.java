package dev.vdrenkov.biblium.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

/**
 * LocalDateMapper component.
 */
public final class LocalDateMapper {
    private static final Logger log = LoggerFactory.getLogger(LocalDateMapper.class);

    private LocalDateMapper() {
        /* This utility class should not be instantiated */
    }

    /**
     * Handles mapStringToDate operation.
     *
     * @param string
     *     Source string value.
     * @return Parsed local date value.
     */
    public static LocalDate mapStringToDate(final String string) {
        log.debug("Date string mapped to date");
        return LocalDate.parse(string);
    }
}
