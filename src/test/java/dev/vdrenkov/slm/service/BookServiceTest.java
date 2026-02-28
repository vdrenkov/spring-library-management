package dev.vdrenkov.slm.service;

import dev.vdrenkov.slm.dto.BookDto;
import dev.vdrenkov.slm.entity.Book;
import dev.vdrenkov.slm.mapper.BookMapper;
import dev.vdrenkov.slm.repository.BookRepository;
import dev.vdrenkov.slm.util.AuthorFactory;
import dev.vdrenkov.slm.util.BookFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static dev.vdrenkov.slm.util.Constants.ID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
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
        when(bookRepository.findAll()).thenReturn(BookFactory.getDefaultBooksList());

        List<Book> testList = bookService.getAllBooksByAuthor(ID);

        assertNotNull(testList);
    }

    @Test
    void testGetAllBooksDtoByAuthor() {
        when(bookRepository.findAll()).thenReturn(BookFactory.getDefaultBooksList());

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
    void testDecreaseBookQuantity_success() {
        Book book = BookFactory.getDefaultBook();
        when(bookRepository.findByIdForUpdate(anyInt())).thenReturn(Optional.of(book));
        when(bookRepository.save(any())).thenReturn(book);

        Book updatedBook = bookService.decreaseBookQuantity(ID);

        assertNotNull(updatedBook);
    }

    @Test
    void testDecreaseBookQuantity_outOfStock_throws() {
        Book outOfStockBook = new Book(
            ID,
            BookFactory.getDefaultBook().getName(),
            BookFactory.getDefaultBook().getPublishDate(),
            BookFactory.getDefaultBook().getAuthor(),
            0
        );
        when(bookRepository.findByIdForUpdate(anyInt())).thenReturn(Optional.of(outOfStockBook));

        assertThrows(IllegalStateException.class, () -> bookService.decreaseBookQuantity(ID));
    }
}
