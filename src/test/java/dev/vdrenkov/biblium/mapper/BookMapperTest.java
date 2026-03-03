package dev.vdrenkov.biblium.mapper;

import dev.vdrenkov.biblium.dto.BookDto;
import dev.vdrenkov.biblium.util.BookFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class BookMapperTest {

    @Test
    void testMapBooksToBooksDto() {
        final List<BookDto> booksDto = BookMapper.mapBooksToBooksDto(BookFactory.getDefaultBooksList());

        assertNotNull(booksDto);
    }

    @Test
    void testMapBookToBookDto() {
        final BookDto bookDto = BookMapper.mapBookToBookDto(BookFactory.getDefaultBook());

        assertNotNull(bookDto);
    }
}
