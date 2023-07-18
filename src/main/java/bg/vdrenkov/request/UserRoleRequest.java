package bg.vdrenkov.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRoleRequest {

  @Pattern(regexp = "^[A-Z_]+$",
           message = "The role should contain only capital letters and/or underscore/s")
  @NotNull(message = "The role can not be null")
  private String role;
}