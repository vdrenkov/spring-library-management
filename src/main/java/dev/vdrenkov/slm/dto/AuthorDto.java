package dev.vdrenkov.slm.dto;

/**
 * AuthorDto component.
 */
public class AuthorDto extends PersonDto {

    /**
     * Handles AuthorDto operation.
     *
     * @param id
     *     Identifier of the target entity.
     * @param name
     *     Name value.
     * @param surname
     *     Surname value.
     */
    public AuthorDto(final int id, final String name, final String surname) {
        super(id, name, surname);
    }
}
