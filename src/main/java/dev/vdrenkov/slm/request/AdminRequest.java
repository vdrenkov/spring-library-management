package dev.vdrenkov.slm.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminRequest {

    @Pattern(regexp = "^[A-Za-z0-9_-]+$",
        message = "The username cannot be null and must consists of letters, numbers, dashes and/or underscores.")
    @NotNull(message = "The username cannot be null")
    private String username;

    @NotNull(message = "The password cannot be null")
    private String password;

    @NotNull(message = "The roles' IDs cannot be null")
    private List<Integer> rolesIds;
}

