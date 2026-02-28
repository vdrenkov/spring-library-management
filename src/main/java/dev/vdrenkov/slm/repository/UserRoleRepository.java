package dev.vdrenkov.slm.repository;

import dev.vdrenkov.slm.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

  Optional<UserRole> findUserRoleByRole(String role);

  boolean existsByRole(String role);
}

