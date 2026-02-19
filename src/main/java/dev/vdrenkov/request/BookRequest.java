package dev.vdrenkov.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookRequest {

  @Pattern(regexp = "^[A-Z0-9][A-Z0-9a-z .]*$",
           message = "The book name must start with a capital letter or digit and should contain only letters, " +
                     "digits, spaces and/or dots")
  @NotNull(message = "The name can not be null")
  private String name;

  @NotNull(message = "The publish date can not be null")
  private LocalDate publishDate;

  @Positive(message = "The author id must be a positive digit")
  @NotNull(message = "The author id can not be null")
  private int authorId;

  @Positive(message = "The quantity must be a positive digit")
  @NotNull(message = "The quantity can not be null")
  private int quantity;
}


