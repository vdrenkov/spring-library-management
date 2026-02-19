package dev.vdrenkov.repository;

import dev.vdrenkov.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

  UserRole findUserRoleByRole(String role);
}

