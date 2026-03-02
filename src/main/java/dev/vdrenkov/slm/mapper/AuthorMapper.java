package dev.vdrenkov.slm.mapper;

import dev.vdrenkov.slm.dto.AuthorDto;
import dev.vdrenkov.slm.entity.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * AuthorMapper component.
 */
@Component
public class AuthorMapper {
    private static final Logger log = LoggerFactory.getLogger(AuthorMapper.class);

    private AuthorMapper() {
        /* This utility class should not be instantiated */
    }

    /**
     * Handles mapAuthorsToAuthorsDto operation.
     *
     * @param authors
     *     Author entities to map.
     * @return List of author DTOs.
     */
    public static List<AuthorDto> mapAuthorsToAuthorsDto(final List<Author> authors) {
        final List<AuthorDto> authorsDto = new ArrayList<>();

        for (final Author author : authors) {
            authorsDto.add(mapAuthorToAuthorDto(author));
        }

        authorsDto.sort(Comparator.comparing(AuthorDto::getId));
        log.debug("Authors' list mapped to authors' DTOs list");
        return authorsDto;
    }

    /**
     * Handles mapAuthorToAuthorDto operation.
     *
     * @param author
     *     Author entity value.
     * @return Resulting author DTO value.
     */
    public static AuthorDto mapAuthorToAuthorDto(final Author author) {
        log.debug("Author mapped to author DTO");
        return new AuthorDto(author.getId(), author.getName(), author.getSurname());
    }
}
