package dev.vdrenkov.slm.service;

import dev.vdrenkov.slm.dto.BookDto;
import dev.vdrenkov.slm.entity.Author;
import dev.vdrenkov.slm.entity.Book;
import dev.vdrenkov.slm.exception.BookNotFoundException;
import dev.vdrenkov.slm.mapper.BookMapper;
import dev.vdrenkov.slm.repository.BookRepository;
import dev.vdrenkov.slm.request.BookRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * BookService component.
 */
@Service
public class BookService {
    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    private final AuthorService authorService;
    private final BookRepository bookRepository;

    /**
     * Handles BookService operation.
     *
     * @param authorService
     *     Service dependency used by this component.
     * @param bookRepository
     *     Repository dependency used by this component.
     */
    @Autowired
    public BookService(final AuthorService authorService, final BookRepository bookRepository) {
        this.authorService = authorService;
        this.bookRepository = bookRepository;
    }

    /**
     * Handles addBook operation.
     *
     * @param bookRequest
     *     Request payload with input data.
     * @return Resulting book value.
     */
    public Book addBook(final BookRequest bookRequest) {
        final Author bookAuthor = authorService.getAuthorById(bookRequest.getAuthorId());

        log.info("Trying to add a new book");
        return bookRepository.save(
            new Book(bookRequest.getName(), bookRequest.getPublishDate(), bookAuthor, bookRequest.getQuantity()));
    }

    /**
     * Handles getAllBooks operation.
     *
     * @return List of books.
     */
    public List<Book> getAllBooks() {
        log.info("Trying to retrieve all books");
        return bookRepository.findAll();
    }

    /**
     * Handles getAllAvailableBooks operation.
     *
     * @return List of books.
     */
    public List<Book> getAllAvailableBooks() {
        return bookRepository.findByQuantityGreaterThan(0);
    }

    /**
     * Handles getAllAvailableBooksDto operation.
     *
     * @return List of book DTOs.
     */
    public List<BookDto> getAllAvailableBooksDto() {
        return BookMapper.mapBooksToBooksDto(getAllAvailableBooks());
    }

    /**
     * Handles getAllBooksByAuthor operation.
     *
     * @param authorId
     *     Identifier of the target entity.
     * @return List of books.
     */
    public List<Book> getAllBooksByAuthor(final int authorId) {
        return bookRepository.findByAuthorIdAndQuantityGreaterThan(authorId, 0);
    }

    /**
     * Handles getAllBooksDtoByAuthor operation.
     *
     * @param authorId
     *     Identifier of the target entity.
     * @return List of book DTOs.
     */
    public List<BookDto> getAllBooksDtoByAuthor(final int authorId) {
        return BookMapper.mapBooksToBooksDto(getAllBooksByAuthor(authorId));
    }

    /**
     * Handles getBookById operation.
     *
     * @param id
     *     Identifier of the target entity.
     * @return Resulting book value.
     */
    public Book getBookById(final int id) {
        log.info("Trying to retrieve book with an ID {}", id);
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    /**
     * Handles getBookDtoById operation.
     *
     * @param id
     *     Identifier of the target entity.
     * @return Resulting book DTO value.
     */
    public BookDto getBookDtoById(final int id) {
        return BookMapper.mapBookToBookDto(getBookById(id));
    }

    /**
     * Handles decreaseBookQuantity operation.
     *
     * @param id
     *     Identifier of the target entity.
     * @return Resulting book value.
     */
    @Transactional
    public Book decreaseBookQuantity(final int id) {
        final Book book = bookRepository.findByIdForUpdate(id).orElseThrow(BookNotFoundException::new);
        if (book.getQuantity() <= 0) {
            throw new IllegalStateException("Book with ID " + id + " is out of stock");
        }

        book.setQuantity(book.getQuantity() - 1);

        log.info("Trying to update book with an ID {}", id);
        return bookRepository.save(book);
    }
}
