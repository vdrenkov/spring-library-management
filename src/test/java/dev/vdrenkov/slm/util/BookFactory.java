package dev.vdrenkov.slm.util;

import dev.vdrenkov.slm.dto.BookDto;
import dev.vdrenkov.slm.entity.Book;
import dev.vdrenkov.slm.request.BookRequest;

import java.util.Collections;
import java.util.List;

import static dev.vdrenkov.slm.util.Constants.BOOKS_NAMES_LIST_VALUE;
import static dev.vdrenkov.slm.util.Constants.ID;
import static dev.vdrenkov.slm.util.Constants.LOCAL_DATE;
import static dev.vdrenkov.slm.util.Constants.NAME;
import static dev.vdrenkov.slm.util.Constants.ONE;
import static dev.vdrenkov.slm.util.Constants.QUANTITY;

public final class BookFactory {

  private BookFactory() {
    throw new IllegalStateException();
  }

  public static Book getDefaultBook() {
    return new Book(ID, NAME, LOCAL_DATE, AuthorFactory.getDefaultAuthor(), QUANTITY);
  }

  public static List<Book> getDefaultBooksList() {
    return Collections.singletonList(getDefaultBook());
  }

  public static BookDto getDefaultBookDto() {
    return new BookDto(ID, NAME, LOCAL_DATE, AuthorFactory.getDefaultAuthorDto(), QUANTITY);
  }

  public static List<BookDto> getDefaultBooksDtoList() {
    return Collections.singletonList(getDefaultBookDto());
  }

  public static BookRequest getDefaultBookRequest() {
    return new BookRequest(NAME, LOCAL_DATE, ID, QUANTITY);
  }

  public static List<String> getDefaultBooksNamesList() {
    return Collections.singletonList(BOOKS_NAMES_LIST_VALUE);
  }

  public static List<Integer> getDefaultBooksIdsList() {
    return Collections.singletonList(ONE);
  }
}

