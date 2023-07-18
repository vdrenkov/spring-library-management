package bg.vdrenkov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class BookDto {

  private final int id;
  private final String name;
  private final LocalDate publishDate;
  private final AuthorDto authorDto;
  private final int quantity;
}
