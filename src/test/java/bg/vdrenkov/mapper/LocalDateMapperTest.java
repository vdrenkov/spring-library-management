package bg.vdrenkov.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static bg.vdrenkov.util.Constants.LOCAL_DATE;

public class LocalDateMapperTest {

  private final LocalDateMapper mapper = new LocalDateMapper();

  @Test
  public void testMapStringToDate() {
    LocalDate response = mapper.mapStringToDate("01-JANUARY-2000");

    Assertions.assertEquals(LOCAL_DATE.toString(), response.toString());
  }
}


