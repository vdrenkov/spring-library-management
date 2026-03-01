package dev.vdrenkov.slm.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static dev.vdrenkov.slm.util.Constants.LOCAL_DATE;

class LocalDateMapperTest {

    private final LocalDateMapper mapper = new LocalDateMapper();

    @Test
    void testMapStringToDate() {
        final LocalDate response = mapper.mapStringToDate("2000-01-01");

        Assertions.assertEquals(LOCAL_DATE.toString(), response.toString());
    }
}
