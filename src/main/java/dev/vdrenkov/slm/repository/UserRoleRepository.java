package dev.vdrenkov.slm.repository;

import dev.vdrenkov.slm.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UserRoleRepository contract.
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

  /**
   * Handles findUserRoleByRole operation.
   * @param role Role value.
   * @return Optional containing the requested userRole.
   */
  Optional<UserRole> findUserRoleByRole(String role);

  /**
   * Handles existsByRole operation.
   * @param role Role value.
   * @return Boolean flag indicating whether the condition is satisfied.
   */
  boolean existsByRole(String role);
}
