package dev.vdrenkov.slm.util;

import dev.vdrenkov.slm.dto.AuthorDto;
import dev.vdrenkov.slm.entity.Author;
import dev.vdrenkov.slm.request.AuthorRequest;

import java.util.Collections;
import java.util.List;

import static dev.vdrenkov.slm.util.Constants.ID;
import static dev.vdrenkov.slm.util.Constants.NAME;
import static dev.vdrenkov.slm.util.Constants.SURNAME;

/**
 * Test factory for creating default {@link Author}, {@link AuthorDto}, and {@link AuthorRequest} instances.
 */
public final class AuthorFactory {

    /**
     * Utility class constructor.
     */
    private AuthorFactory() {
        throw new IllegalStateException("Utility class. Must not be instantiated!");
    }

    /**
     * Creates a default {@link Author} test entity.
     *
     * @return Default author entity.
     */
    public static Author getDefaultAuthor() {
        return new Author(ID, NAME, SURNAME);
    }

    /**
     * Creates a singleton list containing the default author entity.
     *
     * @return Singleton list of authors.
     */
    public static List<Author> getDefaultAuthorsList() {
        return Collections.singletonList(getDefaultAuthor());
    }

    /**
     * Creates a default {@link AuthorDto}.
     *
     * @return Default author DTO.
     */
    public static AuthorDto getDefaultAuthorDto() {
        return new AuthorDto(ID, NAME, SURNAME);
    }

    /**
     * Creates a singleton list containing the default author DTO.
     *
     * @return Singleton list of author DTOs.
     */
    public static List<AuthorDto> getDefaultAuthorsDtoList() {
        return Collections.singletonList(getDefaultAuthorDto());
    }

    /**
     * Creates a default {@link AuthorRequest}.
     *
     * @return Default author request.
     */
    public static AuthorRequest getDefaultAuthorRequest() {
        return new AuthorRequest(NAME, SURNAME);
    }
}
