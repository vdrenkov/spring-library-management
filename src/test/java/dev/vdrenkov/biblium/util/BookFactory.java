package dev.vdrenkov.biblium.util;

import dev.vdrenkov.biblium.dto.BookDto;
import dev.vdrenkov.biblium.entity.Book;
import dev.vdrenkov.biblium.request.BookRequest;

import java.util.Collections;
import java.util.List;

import static dev.vdrenkov.biblium.util.Constants.BOOKS_NAMES_LIST_VALUE;
import static dev.vdrenkov.biblium.util.Constants.ID;
import static dev.vdrenkov.biblium.util.Constants.LOCAL_DATE;
import static dev.vdrenkov.biblium.util.Constants.NAME;
import static dev.vdrenkov.biblium.util.Constants.QUANTITY;

/**
 * Test factory for creating default {@link Book}, {@link BookDto}, and {@link BookRequest} instances.
 */
public final class BookFactory {

    /**
     * Utility class constructor.
     */
    private BookFactory() {
        throw new IllegalStateException("Utility class. Must not be instantiated!");
    }

    /**
     * Creates a default {@link Book} test entity.
     *
     * @return Default book entity.
     */
    public static Book getDefaultBook() {
        return new Book(ID, NAME, LOCAL_DATE, AuthorFactory.getDefaultAuthor(), QUANTITY);
    }

    /**
     * Creates a singleton list containing the default book entity.
     *
     * @return Singleton list of books.
     */
    public static List<Book> getDefaultBooksList() {
        return Collections.singletonList(getDefaultBook());
    }

    /**
     * Creates a default {@link BookDto}.
     *
     * @return Default book DTO.
     */
    public static BookDto getDefaultBookDto() {
        return new BookDto(ID, NAME, LOCAL_DATE, AuthorFactory.getDefaultAuthorDto(), QUANTITY);
    }

    /**
     * Creates a singleton list containing the default book DTO.
     *
     * @return Singleton list of book DTOs.
     */
    public static List<BookDto> getDefaultBooksDtoList() {
        return Collections.singletonList(getDefaultBookDto());
    }

    /**
     * Creates a default {@link BookRequest}.
     *
     * @return Default book request.
     */
    public static BookRequest getDefaultBookRequest() {
        return new BookRequest(NAME, LOCAL_DATE, ID, QUANTITY);
    }

    /**
     * Creates a singleton list containing a default book name value.
     *
     * @return Singleton list of book names.
     */
    public static List<String> getDefaultBooksNamesList() {
        return Collections.singletonList(BOOKS_NAMES_LIST_VALUE);
    }

    /**
     * Creates a singleton list containing a default book ID.
     *
     * @return Singleton list of book IDs.
     */
    public static List<Integer> getDefaultBooksIdsList() {
        return Collections.singletonList(1);
    }
}
