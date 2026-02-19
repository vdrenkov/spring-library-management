package dev.vdrenkov.slm.mapper;

import dev.vdrenkov.slm.dto.AuthorDto;
import dev.vdrenkov.slm.util.AuthorFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthorMapperTest {

    private final AuthorMapper mapper = new AuthorMapper();

    @Test
    void testMapAuthorsToAuthorsDto() {
        List<AuthorDto> authorsDto = mapper.mapAuthorsToAuthorsDto(AuthorFactory.getDefaultAuthorsList());

        assertNotNull(authorsDto);
    }

    @Test
    void testMapAuthorToAuthorDto() {
        AuthorDto authorDto = mapper.mapAuthorToAuthorDto(AuthorFactory.getDefaultAuthor());

        assertNotNull(authorDto);
    }
}

