package bg.vdrenkov.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
public class ClientRequest extends PersonRequest {

  @Pattern(regexp = "^[0-9]{9,10}$", message = "The phone number must consists of only 9 or 10 digits")
  @NotNull(message = "The phone number can not be null")
  private String phoneNumber;
  @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email pattern")
  @NotNull(message = "The email can not be null")
  private String email;

  public ClientRequest(String name, String surname, String phoneNumber, String email) {
    super(name, surname);
    this.phoneNumber = phoneNumber;
    this.email = email;
  }
}