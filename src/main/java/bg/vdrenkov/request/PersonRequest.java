package bg.vdrenkov.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonRequest {

  @Pattern(regexp = "^[A-Z][a-z. ]+$",
           message = "The name should contain only letters, spaces and/or dots and should start with a capital letter")
  @NotNull(message = "The name can not be null")
  private String name;
  @Pattern(regexp = "^[A-Z][a-z. ]+$",
           message = "The name should contain only letters, spaces and/or dots and should start with a capital letter")
  @NotNull(message = "The surname can not be null")
  private String surname;
}
