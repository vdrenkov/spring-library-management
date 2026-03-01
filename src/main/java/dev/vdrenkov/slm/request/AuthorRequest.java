package dev.vdrenkov.slm.request;

import lombok.NoArgsConstructor;

@NoArgsConstructor
/**
 * AuthorRequest component.
 */
public class AuthorRequest extends PersonRequest {

  /**
   * Handles AuthorRequest operation.
   * @param name Name value.
   * @param surname Surname value.
   */
  public AuthorRequest(final String name, final String surname) {
    super(name, surname);
  }
}
