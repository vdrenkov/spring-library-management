package dev.vdrenkov.biblium.service;

import dev.vdrenkov.biblium.dto.AuthorDto;
import dev.vdrenkov.biblium.entity.Author;
import dev.vdrenkov.biblium.exception.AuthorNotFoundException;
import dev.vdrenkov.biblium.mapper.AuthorMapper;
import dev.vdrenkov.biblium.repository.AuthorRepository;
import dev.vdrenkov.biblium.request.AuthorRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AuthorService component.
 */
@Service
public class AuthorService {
    private static final Logger log = LoggerFactory.getLogger(AuthorService.class);

    private final AuthorRepository authorRepository;

    /**
     * Handles AuthorService operation.
     *
     * @param authorRepository
     *     Repository dependency used by this component.
     */
    @Autowired
    public AuthorService(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Handles addAuthor operation.
     *
     * @param authorRequest
     *     Request payload with input data.
     * @return Resulting author value.
     */
    public Author addAuthor(final AuthorRequest authorRequest) {
        final Author author = new Author(authorRequest.getName(), authorRequest.getSurname());

        log.info("Trying to add a new author");
        return authorRepository.save(author);
    }

    /**
     * Handles getAllAuthors operation.
     *
     * @return List of authors.
     */
    public List<Author> getAllAuthors() {
        log.info("Trying to retrieve all authors");
        return authorRepository.findAll();
    }

    /**
     * Handles getAllAuthorsDto operation.
     *
     * @return List of author DTOs.
     */
    public List<AuthorDto> getAllAuthorsDto() {
        return AuthorMapper.mapAuthorsToAuthorsDto(getAllAuthors());
    }

    /**
     * Handles getAuthorById operation.
     *
     * @param id
     *     Identifier of the target entity.
     * @return Resulting author value.
     */
    public Author getAuthorById(final int id) {
        log.info("Trying to retrieve author with an ID {}", id);
        return authorRepository.findById(id).orElseThrow(AuthorNotFoundException::new);
    }

    /**
     * Handles getAuthorDtoById operation.
     *
     * @param id
     *     Identifier of the target entity.
     * @return Resulting author DTO value.
     */
    public AuthorDto getAuthorDtoById(final int id) {
        return AuthorMapper.mapAuthorToAuthorDto(getAuthorById(id));
    }
}
