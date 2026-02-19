package dev.vdrenkov.slm.dto;

import lombok.Getter;

import java.util.Objects;

@Getter
public class ClientDto extends PersonDto {

    private final String phoneNumber;
    private final String email;

    public ClientDto(int id, String name, String surname, String phoneNumber, String email) {
        super(id, name, surname);
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final ClientDto clientDto))
            return false;
        if (!super.equals(o))
            return false;
        return Objects.equals(phoneNumber, clientDto.phoneNumber) && Objects.equals(email, clientDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phoneNumber, email);
    }
}
