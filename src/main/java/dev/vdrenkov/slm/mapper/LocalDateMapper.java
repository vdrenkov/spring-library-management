package dev.vdrenkov.slm.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LocalDateMapper {

  private static final Logger log = LoggerFactory.getLogger(LocalDateMapper.class);

  public LocalDate mapStringToDate(final String string) {
    log.debug("Date string mapped to date");
    return LocalDate.parse(string);
  }
}
