package bg.vdrenkov.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class UserRequest {

  @NotNull(message = "The username can not be null")
  private String username;

  @NotNull(message = "The password can not be null")
  private String password;
}


