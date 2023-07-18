package bg.vdrenkov.test.util;

import bg.vdrenkov.dto.AuthorDto;
import bg.vdrenkov.entity.Author;
import bg.vdrenkov.request.AuthorRequest;

import java.util.Collections;
import java.util.List;

import static bg.vdrenkov.util.Constants.ID;
import static bg.vdrenkov.util.Constants.NAME;
import static bg.vdrenkov.util.Constants.SURNAME;

public final class AuthorFactory {

  private AuthorFactory() {
    throw new IllegalStateException();
  }

  public static Author getDefaultAuthor() {
    return new Author(ID, NAME, SURNAME);
  }

  public static List<Author> getDefaultAuthorsList() {
    return Collections.singletonList(getDefaultAuthor());
  }

  public static AuthorDto getDefaultAuthorDto() {
    return new AuthorDto(ID, NAME, SURNAME);
  }

  public static List<AuthorDto> getDefaultAuthorsDtoList() {
    return Collections.singletonList(getDefaultAuthorDto());
  }

  public static AuthorRequest getDefaultAuthorRequest() {
    return new AuthorRequest(NAME, SURNAME);
  }
}
