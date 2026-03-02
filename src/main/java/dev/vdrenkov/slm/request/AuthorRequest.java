package dev.vdrenkov.slm.request;

import lombok.NoArgsConstructor;

/**
 * AuthorRequest component.
 */
@NoArgsConstructor
public class AuthorRequest extends PersonRequest {

    /**
     * Handles AuthorRequest operation.
     *
     * @param name
     *     Name value.
     * @param surname
     *     Surname value.
     */
    public AuthorRequest(final String name, final String surname) {
        super(name, surname);
    }
}
