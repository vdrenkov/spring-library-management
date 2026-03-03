package dev.vdrenkov.biblium.dto;

import lombok.Getter;

import java.util.Objects;

/**
 * ClientDto component.
 */
@Getter
public class ClientDto extends PersonDto {
    private final String phoneNumber;
    private final String email;

    /**
     * Handles ClientDto operation.
     *
     * @param id
     *     Identifier of the target entity.
     * @param name
     *     Name value.
     * @param surname
     *     Surname value.
     * @param phoneNumber
     *     Phone number value.
     * @param email
     *     Email value.
     */
    public ClientDto(final int id, final String name, final String surname, final String phoneNumber,
        final String email) {
        super(id, name, surname);
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /**
     * Handles equals operation.
     *
     * @param o
     *     Object compared with the current instance.
     * @return Boolean flag indicating whether the condition is satisfied.
     */
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final ClientDto clientDto))
            return false;
        if (!super.equals(o))
            return false;
        return Objects.equals(phoneNumber, clientDto.phoneNumber) && Objects.equals(email, clientDto.email);
    }

    /**
     * Handles hashCode operation.
     *
     * @return Numeric result of the operation.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phoneNumber, email);
    }
}
