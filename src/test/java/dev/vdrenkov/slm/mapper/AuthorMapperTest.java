package dev.vdrenkov.slm.mapper;

import dev.vdrenkov.slm.dto.AuthorDto;
import dev.vdrenkov.slm.util.AuthorFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthorMapperTest {

    @Test
    void testMapAuthorsToAuthorsDto() {
        final List<AuthorDto> authorsDto = AuthorMapper.mapAuthorsToAuthorsDto(AuthorFactory.getDefaultAuthorsList());

        assertNotNull(authorsDto);
    }

    @Test
    void testMapAuthorToAuthorDto() {
        final AuthorDto authorDto = AuthorMapper.mapAuthorToAuthorDto(AuthorFactory.getDefaultAuthor());

        assertNotNull(authorDto);
    }
}
