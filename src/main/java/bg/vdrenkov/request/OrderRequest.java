package bg.vdrenkov.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequest {

  @Positive(message = "The client id must be a positive digit")
  @NotNull(message = "The client id can not be null")
  private int clientId;
  @NotNull(message = "The books' ids can not be null")
  private List<Integer> booksIds;
}
