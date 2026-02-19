package dev.vdrenkov.slm.mapper;

import dev.vdrenkov.slm.dto.BookDto;
import dev.vdrenkov.slm.util.AuthorFactory;
import dev.vdrenkov.slm.util.BookFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookMapperTest {

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private BookMapper mapper;

    @Test
    void testMapBooksToBooksDto() {
        when(authorMapper.mapAuthorToAuthorDto(any())).thenReturn(AuthorFactory.getDefaultAuthorDto());

        List<BookDto> booksDto = mapper.mapBooksToBooksDto(BookFactory.getDefaultBooksList());

        assertNotNull(booksDto);
    }

    @Test
    void testMapBookToBookDto() {
        when(authorMapper.mapAuthorToAuthorDto(any())).thenReturn(AuthorFactory.getDefaultAuthorDto());

        BookDto bookDto = mapper.mapBookToBookDto(BookFactory.getDefaultBook());

        assertNotNull(bookDto);
    }
}

