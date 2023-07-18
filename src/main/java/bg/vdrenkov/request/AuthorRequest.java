package bg.vdrenkov.request;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthorRequest extends PersonRequest {

  public AuthorRequest(String name, String surname) {
    super(name, surname);
  }
}
