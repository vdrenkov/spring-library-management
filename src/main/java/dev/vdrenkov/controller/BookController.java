package dev.vdrenkov.controller;

import dev.vdrenkov.dto.BookDto;
import dev.vdrenkov.entity.Book;
import dev.vdrenkov.request.BookRequest;
import dev.vdrenkov.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
public class BookController {

  private static final Logger log = LoggerFactory.getLogger(BookController.class);

  private final String URI = "/books";
  private final BookService bookService;

  @Autowired
  public BookController(
    BookService bookService) {
    this.bookService = bookService;
  }

  @PostMapping(URI)
  public ResponseEntity<Void> addBook(@RequestBody @Valid BookRequest bookRequest) {
    Book book =
      bookService.addBook(bookRequest);

    URI location = UriComponentsBuilder.fromUriString("/books/{id}")
                                       .buildAndExpand(book.getId())
                                       .toUri();
    log.info("A new book was added");
    return ResponseEntity.created(location).build();
  }

  @GetMapping(URI)
  public ResponseEntity<List<BookDto>> getAllAvailableBooks() {
    log.info("All available books were requested from the database");
    return ResponseEntity.ok(bookService.getAllAvailableBooksDto());
  }

  @GetMapping("/authors/{authorId}" + URI)
  public ResponseEntity<List<BookDto>> getAllBooksByAuthor(@PathVariable int authorId) {
    log.info(String.format("All books with author id %d were requested from the database", authorId));
    return ResponseEntity.ok(bookService.getAllBooksDtoByAuthor(authorId));
  }

  @GetMapping(URI + "/{id}")
  public ResponseEntity<BookDto> getBookById(@PathVariable int id) {
    log.info(String.format("Book with id %d was requested from the database", id));
    return ResponseEntity.ok(bookService.getBookDtoById(id));
  }
}


