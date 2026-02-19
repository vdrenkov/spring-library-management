package dev.vdrenkov.mapper;

import dev.vdrenkov.dto.BookDto;
import dev.vdrenkov.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class BookMapper {

  private static final Logger log = LoggerFactory.getLogger(BookMapper.class);

  private final AuthorMapper authorMapper;

  @Autowired
  public BookMapper(AuthorMapper authorMapper) {
    this.authorMapper = authorMapper;
  }

  public List<BookDto> mapBooksToBooksDto(List<Book> books) {
    List<BookDto> booksDto = new ArrayList<>();

    for (Book book : books) {
      booksDto.add(mapBookToBookDto(book));
    }

    booksDto.sort(Comparator.comparing(BookDto::id));
    log.info("Books' list mapped to books' DTOs list");
    return booksDto;
  }

  public BookDto mapBookToBookDto(Book book) {
    log.info("Book mapped to book DTO");
    return new BookDto(book.getId(), book.getName(), book.getPublishDate(),
                       authorMapper.mapAuthorToAuthorDto(book.getAuthor()),
                       book.getQuantity());
  }
}
