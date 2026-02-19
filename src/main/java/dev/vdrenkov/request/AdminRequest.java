package dev.vdrenkov.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminRequest {

  @Pattern(regexp = "^[A-Za-z0-9_-]+$",
           message = "The username can not be null and must consists of letters, numbers, dashes and/or underscores")
  @NotNull(message = "The username can not be null")
  private String username;

  @NotNull(message = "The password can not be null")
  private String password;

  @NotNull(message = "The roles' ids can not be null")
  private List<Integer> rolesIds;
}

