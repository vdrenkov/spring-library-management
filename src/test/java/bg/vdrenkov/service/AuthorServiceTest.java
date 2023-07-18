package bg.vdrenkov.service;

import bg.vdrenkov.dto.AuthorDto;
import bg.vdrenkov.entity.Author;
import bg.vdrenkov.mapper.AuthorMapper;
import bg.vdrenkov.repository.AuthorRepository;
import bg.vdrenkov.test.util.AuthorFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static bg.vdrenkov.util.Constants.ID;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceTest {

  @InjectMocks
  private AuthorService authorService;

  @Mock
  private AuthorRepository authorRepository;

  @Mock
  private AuthorMapper authorMapper;

  @Test
  public void testAddAuthor() {
    when(authorRepository.save(any())).thenReturn(new Author());

    Author author = authorService.addAuthor(AuthorFactory.getDefaultAuthorRequest());

    Assert.assertNotNull(author);
  }

  @Test
  public void testGetAllAuthors() {
    when(authorRepository.findAll()).thenReturn(AuthorFactory.getDefaultAuthorsList());

    List<Author> result = authorService.getAllAuthors();

    assertFalse(result.isEmpty());
  }

  @Test
  public void testGetAllAuthorsDto() {
    when(authorRepository.findAll()).thenReturn(AuthorFactory.getDefaultAuthorsList());
    when(authorMapper.mapAuthorsToAuthorsDto(anyList())).thenReturn(AuthorFactory.getDefaultAuthorsDtoList());

    List<AuthorDto> result = authorService.getAllAuthorsDto();

    assertFalse(result.isEmpty());
  }

  @Test
  public void testGetAuthorById() {
    when(authorRepository.findById(anyInt())).thenReturn(Optional.of(AuthorFactory.getDefaultAuthor()));

    Author result = authorService.getAuthorById(ID);

    assertNotNull(result.getName());
  }

  @Test
  public void testGetAuthorDtoById() {
    when(authorRepository.findById(anyInt())).thenReturn(Optional.of(AuthorFactory.getDefaultAuthor()));
    when(authorMapper.mapAuthorToAuthorDto(any())).thenReturn(AuthorFactory.getDefaultAuthorDto());

    AuthorDto result = authorService.getAuthorDtoById(ID);

    assertNotNull(result.getName());
  }
}