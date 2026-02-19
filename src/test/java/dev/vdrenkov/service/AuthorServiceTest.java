package dev.vdrenkov.service;

import dev.vdrenkov.dto.AuthorDto;
import dev.vdrenkov.entity.Author;
import dev.vdrenkov.mapper.AuthorMapper;
import dev.vdrenkov.repository.AuthorRepository;
import dev.vdrenkov.util.AuthorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static dev.vdrenkov.util.Constants.ID;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @InjectMocks
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @Test
    void testAddAuthor() {
        when(authorRepository.save(any())).thenReturn(new Author());

        Author author = authorService.addAuthor(AuthorFactory.getDefaultAuthorRequest());

        Assertions.assertNotNull(author);
    }

    @Test
    void testGetAllAuthors() {
        when(authorRepository.findAll()).thenReturn(AuthorFactory.getDefaultAuthorsList());

        List<Author> result = authorService.getAllAuthors();

        assertFalse(result.isEmpty());
    }

    @Test
    void testGetAllAuthorsDto() {
        when(authorRepository.findAll()).thenReturn(AuthorFactory.getDefaultAuthorsList());
        when(authorMapper.mapAuthorsToAuthorsDto(anyList())).thenReturn(AuthorFactory.getDefaultAuthorsDtoList());

        List<AuthorDto> result = authorService.getAllAuthorsDto();

        assertFalse(result.isEmpty());
    }

    @Test
    void testGetAuthorById() {
        when(authorRepository.findById(anyInt())).thenReturn(Optional.of(AuthorFactory.getDefaultAuthor()));

        Author result = authorService.getAuthorById(ID);

        assertNotNull(result.getName());
    }

    @Test
    void testGetAuthorDtoById() {
        when(authorRepository.findById(anyInt())).thenReturn(Optional.of(AuthorFactory.getDefaultAuthor()));
        when(authorMapper.mapAuthorToAuthorDto(any())).thenReturn(AuthorFactory.getDefaultAuthorDto());

        AuthorDto result = authorService.getAuthorDtoById(ID);

        assertNotNull(result.getName());
    }
}


