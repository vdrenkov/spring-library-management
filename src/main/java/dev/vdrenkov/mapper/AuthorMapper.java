package dev.vdrenkov.mapper;

import dev.vdrenkov.dto.AuthorDto;
import dev.vdrenkov.entity.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class AuthorMapper {

  private static final Logger log = LoggerFactory.getLogger(AuthorMapper.class);

  public List<AuthorDto> mapAuthorsToAuthorsDto(List<Author> authors) {
    List<AuthorDto> authorsDto = new ArrayList<>();

    for (Author author : authors) {
      authorsDto.add(mapAuthorToAuthorDto(author));
    }

    authorsDto.sort(Comparator.comparing(AuthorDto::getId));
    log.info("Authors' list mapped to authors' DTOs list");
    return authorsDto;
  }

  public AuthorDto mapAuthorToAuthorDto(Author author) {
    log.info("Author mapped to author DTO");
    return new AuthorDto(author.getId(), author.getName(), author.getSurname());
  }
}

