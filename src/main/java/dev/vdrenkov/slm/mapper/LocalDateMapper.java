package dev.vdrenkov.slm.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
/**
 * LocalDateMapper component.
 */
public class LocalDateMapper {

  private static final Logger log = LoggerFactory.getLogger(LocalDateMapper.class);

  /**
   * Handles mapStringToDate operation.
   * @param string Source string value.
   * @return Parsed local date value.
   */
  public LocalDate mapStringToDate(final String string) {
    log.debug("Date string mapped to date");
    return LocalDate.parse(string);
  }
}
