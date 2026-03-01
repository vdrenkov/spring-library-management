package dev.vdrenkov.slm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Person component.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@MappedSuperclass
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected int id;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "SURNAME")
    protected String surname;

    /**
     * Handles Person operation.
     *
     * @param name
     *     Name value.
     * @param surname
     *     Surname value.
     */
    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}
