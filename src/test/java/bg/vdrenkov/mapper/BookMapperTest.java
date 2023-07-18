package bg.vdrenkov.mapper;

import bg.vdrenkov.dto.BookDto;
import bg.vdrenkov.test.util.AuthorFactory;
import bg.vdrenkov.test.util.BookFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookMapperTest {

  @Mock
  private AuthorMapper authorMapper;

  @InjectMocks
  private BookMapper mapper;

  @Test
  public void testMapBooksToBooksDto() {
    when(authorMapper.mapAuthorToAuthorDto(any())).thenReturn(AuthorFactory.getDefaultAuthorDto());

    List<BookDto> booksDto = mapper.mapBooksToBooksDto(BookFactory.getDefaultBooksList());

    assertNotNull(booksDto);
  }

  @Test
  public void testMapBookToBookDto() {
    when(authorMapper.mapAuthorToAuthorDto(any())).thenReturn(AuthorFactory.getDefaultAuthorDto());

    BookDto bookDto = mapper.mapBookToBookDto(BookFactory.getDefaultBook());

    assertNotNull(bookDto);
  }
}