package bg.vdrenkov.service;

import bg.vdrenkov.dto.AuthorDto;
import bg.vdrenkov.entity.Author;
import bg.vdrenkov.exception.AuthorNotFoundException;
import bg.vdrenkov.mapper.AuthorMapper;
import bg.vdrenkov.repository.AuthorRepository;
import bg.vdrenkov.request.AuthorRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

  private static final Logger log = LoggerFactory.getLogger(AuthorService.class);

  private final AuthorRepository authorRepository;
  private final AuthorMapper authorMapper;

  @Autowired
  public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
    this.authorRepository = authorRepository;
    this.authorMapper = authorMapper;
  }

  public Author addAuthor(AuthorRequest authorRequest) {
    Author author = new Author(authorRequest.getName(), authorRequest.getSurname());

    log.info("Trying to add a new author");
    return authorRepository.save(author);
  }

  public List<Author> getAllAuthors() {
    log.info("Trying to retrieve all authors");
    return authorRepository.findAll();
  }

  public List<AuthorDto> getAllAuthorsDto() {
    return authorMapper.mapAuthorsToAuthorsDto(getAllAuthors());
  }

  public Author getAuthorById(int id) {
    log.info(String.format("Trying to retrieve author with id %d", id));
    return authorRepository.findById(id).orElseThrow(AuthorNotFoundException::new);
  }

  public AuthorDto getAuthorDtoById(int id) {
    return authorMapper.mapAuthorToAuthorDto(getAuthorById(id));
  }
}

