package dev.vdrenkov.slm.mapper;

import dev.vdrenkov.slm.dto.BookDto;
import dev.vdrenkov.slm.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
/**
 * BookMapper component.
 */
public class BookMapper {

  private static final Logger log = LoggerFactory.getLogger(BookMapper.class);

  private final AuthorMapper authorMapper;

  @Autowired
  /**
   * Handles BookMapper operation.
   * @param authorMapper Mapper dependency used by this component.
   */
  public BookMapper(final AuthorMapper authorMapper) {
    this.authorMapper = authorMapper;
  }

  /**
   * Handles mapBooksToBooksDto operation.
   * @param books Books included in the order.
   * @return List of book DTOs.
   */
  public List<BookDto> mapBooksToBooksDto(final List<Book> books) {
    final List<BookDto> booksDto = new ArrayList<>();

    for (final Book book : books) {
      booksDto.add(mapBookToBookDto(book));
    }

    booksDto.sort(Comparator.comparing(BookDto::id));
    log.debug("Books' list mapped to books' DTOs list");
    return booksDto;
  }

  /**
   * Handles mapBookToBookDto operation.
   * @param book Book entity value.
   * @return Resulting book DTO value.
   */
  public BookDto mapBookToBookDto(final Book book) {
    log.debug("Book mapped to book DTO");
    return new BookDto(book.getId(), book.getName(), book.getPublishDate(),
                       authorMapper.mapAuthorToAuthorDto(book.getAuthor()),
                       book.getQuantity());
  }
}
