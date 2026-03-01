package dev.vdrenkov.slm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserRole component.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "USER_ROLES")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "ROLE", unique = true)
    private String role;

    /**
     * Handles UserRole operation.
     *
     * @param role
     *     Role value.
     */
    public UserRole(final String role) {
        this.role = role;
    }
}
