package dev.vdrenkov.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserRequest {

    @NotNull(message = "The username cannot be null")
    private String username;

    @NotNull(message = "The password cannot be null")
    private String password;
}


