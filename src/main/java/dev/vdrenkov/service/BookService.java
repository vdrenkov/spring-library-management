package dev.vdrenkov.service;

import dev.vdrenkov.dto.BookDto;
import dev.vdrenkov.entity.Author;
import dev.vdrenkov.entity.Book;
import dev.vdrenkov.exception.BookNotFoundException;
import dev.vdrenkov.mapper.BookMapper;
import dev.vdrenkov.repository.BookRepository;
import dev.vdrenkov.request.BookRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static dev.vdrenkov.util.Constants.ZERO;

@Service
public class BookService {

  private static final Logger log = LoggerFactory.getLogger(AuthorService.class);

  private final AuthorService authorService;
  private final BookRepository bookRepository;
  private final BookMapper bookMapper;

  @Autowired
  public BookService(
    AuthorService authorService, BookRepository bookRepository, BookMapper bookMapper) {
    this.authorService = authorService;
    this.bookRepository = bookRepository;
    this.bookMapper = bookMapper;
  }

  public Book addBook(BookRequest bookRequest) {
    Author bookAuthor = authorService.getAuthorById(bookRequest.getAuthorId());

    log.info("Trying to add a new book");
    return bookRepository.save(
      new Book(bookRequest.getName(), bookRequest.getPublishDate(), bookAuthor, bookRequest.getQuantity()));
  }

  public List<Book> getAllBooks() {
    log.info("Trying to retrieve all books");
    return bookRepository.findAll();
  }

  public List<Book> getAllAvailableBooks() {
    List<Book> books = getAllBooks();
    return filterBooksByAvailability(books);
  }

  public List<Book> filterBooksByAvailability(List<Book> books) {
    List<Book> availableBooks = new ArrayList<>();

    for (Book book : books) {
      if (book.getQuantity() > ZERO) {
        availableBooks.add(book);
      }
    }
    return availableBooks;
  }

  public List<BookDto> getAllAvailableBooksDto() {
    return bookMapper.mapBooksToBooksDto(getAllAvailableBooks());
  }

  public List<Book> getAllBooksByAuthor(int authorId) {
    List<Book> allAvailableBooksByAuthor = new ArrayList<>();
    List<Book> allBooks = getAllAvailableBooks();

    for (Book book : allBooks) {
      if (book.getAuthor().getId() == authorId) {
        allAvailableBooksByAuthor.add(book);
      }
    }
    return allAvailableBooksByAuthor;
  }

  public List<BookDto> getAllBooksDtoByAuthor(int authorId) {
    return bookMapper.mapBooksToBooksDto(getAllBooksByAuthor(authorId));
  }

  public Book getBookById(int id) {
    log.info(String.format("Trying to retrieve book with id %d", id));
    return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
  }

  public BookDto getBookDtoById(int id) {
    return bookMapper.mapBookToBookDto(getBookById(id));
  }

  public boolean isBookAvailable(int id) {
    List<Book> books = getAllAvailableBooks();

    for (Book book : books) {
      if (book.getId() == id) {
        return true;
      }
    }
    return false;
  }

  public Book updateBookQuantity(int id) {
    Book book = getBookById(id);

    book.setQuantity(book.getQuantity() - 1);

    log.info(String.format("Trying to update book with id %d", id));
    return bookRepository.save(book);
  }
}
