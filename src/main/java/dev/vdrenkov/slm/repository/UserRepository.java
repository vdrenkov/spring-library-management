package dev.vdrenkov.slm.repository;

import dev.vdrenkov.slm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
/**
 * UserRepository contract.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

  /**
   * Handles findByUsername operation.
   * @param username Username value.
   * @return Optional containing the requested user.
   */
  Optional<User> findByUsername(String username);

  /**
   * Handles existsByUsername operation.
   * @param username Username value.
   * @return Boolean flag indicating whether the condition is satisfied.
   */
  boolean existsByUsername(String username);
}
