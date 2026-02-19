package dev.vdrenkov.slm.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequest {

    @Positive(message = "The client ID must be a positive digit")
    @NotNull(message = "The client ID cannot be null")
    private int clientId;
    @NotNull(message = "The books' IDs cannot be null")
    private List<Integer> booksIds;
}


