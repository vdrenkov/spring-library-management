package bg.vdrenkov.mapper;

import bg.vdrenkov.dto.AuthorDto;
import bg.vdrenkov.test.util.AuthorFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthorMapperTest {

  private final AuthorMapper mapper = new AuthorMapper();

  @Test
  public void testMapAuthorsToAuthorsDto() {
    List<AuthorDto> authorsDto = mapper.mapAuthorsToAuthorsDto(AuthorFactory.getDefaultAuthorsList());

    assertNotNull(authorsDto);
  }

  @Test
  public void testMapAuthorToAuthorDto() {
    AuthorDto authorDto = mapper.mapAuthorToAuthorDto(AuthorFactory.getDefaultAuthor());

    assertNotNull(authorDto);
  }
}

