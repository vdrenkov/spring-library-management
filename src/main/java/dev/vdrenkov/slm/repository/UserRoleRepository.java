package dev.vdrenkov.slm.repository;

import dev.vdrenkov.slm.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

  UserRole findUserRoleByRole(String role);
}

