package bg.vdrenkov.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

@Component
public class LocalDateMapper {

  private static final Logger log = LoggerFactory.getLogger(LocalDateMapper.class);

  private static final String DATETIME_PATTERN = "dd-MMMM-yyyy";

  public LocalDate mapStringToDate(String string) {
    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .append(DateTimeFormatter.ofPattern(DATETIME_PATTERN))
      .toFormatter(Locale.ENGLISH);
    log.info("Date string mapped to date");
    return LocalDate.parse(string, formatter);
  }
}
