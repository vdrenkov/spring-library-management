package dev.vdrenkov.biblium.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.NoArgsConstructor;

/**
 * Author component.
 */
@NoArgsConstructor
@Entity
@Table(name = "authors", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "surname" }))
public class Author extends Person {
    /**
     * Handles Author operation.
     *
     * @param id
     *     Identifier of the target entity.
     * @param name
     *     Name value.
     * @param surname
     *     Surname value.
     */
    public Author(final int id, final String name, final String surname) {
        super(id, name, surname);
    }

    /**
     * Handles Author operation.
     *
     * @param name
     *     Name value.
     * @param surname
     *     Surname value.
     */
    public Author(final String name, final String surname) {
        super(name, surname);
    }
}
