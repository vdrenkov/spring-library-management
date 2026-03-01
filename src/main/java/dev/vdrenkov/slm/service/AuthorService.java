package dev.vdrenkov.slm.service;

import dev.vdrenkov.slm.dto.AuthorDto;
import dev.vdrenkov.slm.entity.Author;
import dev.vdrenkov.slm.exception.AuthorNotFoundException;
import dev.vdrenkov.slm.mapper.AuthorMapper;
import dev.vdrenkov.slm.repository.AuthorRepository;
import dev.vdrenkov.slm.request.AuthorRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
/**
 * AuthorService component.
 */
public class AuthorService {

    private static final Logger log = LoggerFactory.getLogger(AuthorService.class);

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    /**
     * Handles AuthorService operation.
     * @param authorRepository Repository dependency used by this component.
     * @param authorMapper Mapper dependency used by this component.
     */
    public AuthorService(final AuthorRepository authorRepository, final AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    /**
     * Handles addAuthor operation.
     * @param authorRequest Request payload with input data.
     * @return Resulting author value.
     */
    public Author addAuthor(final AuthorRequest authorRequest) {
        final Author author = new Author(authorRequest.getName(), authorRequest.getSurname());

        log.info("Trying to add a new author");
        return authorRepository.save(author);
    }

    /**
     * Handles getAllAuthors operation.
     * @return List of authors.
     */
    public List<Author> getAllAuthors() {
        log.info("Trying to retrieve all authors");
        return authorRepository.findAll();
    }

    /**
     * Handles getAllAuthorsDto operation.
     * @return List of author DTOs.
     */
    public List<AuthorDto> getAllAuthorsDto() {
        return authorMapper.mapAuthorsToAuthorsDto(getAllAuthors());
    }

    /**
     * Handles getAuthorById operation.
     * @param id Identifier of the target entity.
     * @return Resulting author value.
     */
    public Author getAuthorById(final int id) {
        log.info("Trying to retrieve author with an ID {}", id);
        return authorRepository.findById(id).orElseThrow(AuthorNotFoundException::new);
    }

    /**
     * Handles getAuthorDtoById operation.
     * @param id Identifier of the target entity.
     * @return Resulting author DTO value.
     */
    public AuthorDto getAuthorDtoById(final int id) {
        return authorMapper.mapAuthorToAuthorDto(getAuthorById(id));
    }
}
