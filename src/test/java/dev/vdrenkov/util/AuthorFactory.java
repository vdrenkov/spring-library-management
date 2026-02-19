package dev.vdrenkov.util;

import dev.vdrenkov.dto.AuthorDto;
import dev.vdrenkov.entity.Author;
import dev.vdrenkov.request.AuthorRequest;

import java.util.Collections;
import java.util.List;

import static dev.vdrenkov.util.Constants.ID;
import static dev.vdrenkov.util.Constants.NAME;
import static dev.vdrenkov.util.Constants.SURNAME;

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

