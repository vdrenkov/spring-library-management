package bg.vdrenkov.test.util;

import bg.vdrenkov.dto.BookDto;
import bg.vdrenkov.entity.Book;
import bg.vdrenkov.request.BookRequest;

import java.util.Collections;
import java.util.List;

import static bg.vdrenkov.util.Constants.BOOKS_NAMES_LIST_VALUE;
import static bg.vdrenkov.util.Constants.ID;
import static bg.vdrenkov.util.Constants.LOCAL_DATE;
import static bg.vdrenkov.util.Constants.NAME;
import static bg.vdrenkov.util.Constants.ONE;
import static bg.vdrenkov.util.Constants.QUANTITY;

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

