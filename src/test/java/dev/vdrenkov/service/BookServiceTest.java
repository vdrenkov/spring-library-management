package dev.vdrenkov.service;

import dev.vdrenkov.dto.BookDto;
import dev.vdrenkov.entity.Book;
import dev.vdrenkov.mapper.BookMapper;
import dev.vdrenkov.repository.BookRepository;
import dev.vdrenkov.util.AuthorFactory;
import dev.vdrenkov.util.BookFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static dev.vdrenkov.util.Constants.ID;
import static dev.vdrenkov.util.Constants.ZERO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class BookServiceTest {

  @Mock
  private AuthorService authorService;

  @Mock
  private BookRepository bookRepository;

  @Mock
  private BookMapper bookMapper;

  @InjectMocks
  private BookService bookService;

  @Test
   void testAddBook_success() {
    when(authorService.getAuthorById(anyInt())).thenReturn(AuthorFactory.getDefaultAuthor());
    when(bookRepository.save(any())).thenReturn(new Book());

   Book book = bookService.addBook(BookFactory.getDefaultBookRequest());

    assertNotNull(book);
  }

  @Test
   void testAddBook_failure() {
    when(authorService.getAuthorById(anyInt())).thenReturn(null);

    Book book = bookService.addBook(BookFactory.getDefaultBookRequest());

    assertNull(book);
  }

  @Test
   void testGetAllBooks() {
    when(bookRepository.findAll()).thenReturn(BookFactory.getDefaultBooksList());

    List<Book> testList = bookService.getAllBooks();

    assertNotNull(testList);
  }

  @Test
   void testGetAllAvailableBooks() {
    when(bookRepository.findAll()).thenReturn(BookFactory.getDefaultBooksList());

    List<Book> testList = bookService.getAllAvailableBooks();

    assertNotNull(testList);
  }

  @Test
   void testFilterBooksByAvailability() {
    List<Book> testBooks = bookService.filterBooksByAvailability(BookFactory.getDefaultBooksList());

    assertNotNull(testBooks);
  }

  @Test
   void testGetAllAvailableBooksDto() {
    when(bookMapper.mapBooksToBooksDto(anyList())).thenReturn(BookFactory.getDefaultBooksDtoList());

    List<BookDto> testList = bookService.getAllAvailableBooksDto();

    assertNotNull(testList);
  }

  @Test
   void testGetAllBooksByAuthor() {
    when(bookService.getAllBooks()).thenReturn(BookFactory.getDefaultBooksList());

    List<Book> testList = bookService.getAllBooksByAuthor(ID);

    assertNotNull(testList);
  }

  @Test
   void testGetAllBooksDtoByAuthor() {
    when(bookService.getAllBooks()).thenReturn(BookFactory.getDefaultBooksList());

    List<BookDto> testList = bookService.getAllBooksDtoByAuthor(ID);

    assertNotNull(testList);
  }

  @Test
   void testGetBookById() {
    when(bookRepository.findById(anyInt())).thenReturn(Optional.of(BookFactory.getDefaultBook()));

    Book book = bookService.getBookById(ID);

    assertNotNull(book);
  }

  @Test
   void testGetBookDtoById() {
    when(bookRepository.findById(anyInt())).thenReturn(Optional.of(BookFactory.getDefaultBook()));
    when(bookMapper.mapBookToBookDto(any())).thenReturn(BookFactory.getDefaultBookDto());

    BookDto bookDto = bookService.getBookDtoById(ID);

    assertNotNull(bookDto);
  }

  @Test
   void testIsBookAvailable_true() {
    when(bookService.getAllBooks()).thenReturn(BookFactory.getDefaultBooksList());

    boolean result = bookService.isBookAvailable(ID);

    assertTrue(result);
  }

  @Test
   void testIsBookAvailable_false() {
    when(bookService.getAllBooks()).thenReturn(BookFactory.getDefaultBooksList());

    boolean result = bookService.isBookAvailable(ZERO);

    assertFalse(result);
  }

  @Test
   void testUpdateBook_returnsTrue() {
    when(bookRepository.findById(anyInt())).thenReturn(Optional.of(BookFactory.getDefaultBook()));
    when(bookRepository.save(any())).thenReturn(BookFactory.getDefaultBook());

    Book book = bookService.updateBookQuantity(ID);

    assertNotNull(book);
  }
}

