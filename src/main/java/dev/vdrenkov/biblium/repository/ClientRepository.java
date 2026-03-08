package dev.vdrenkov.biblium.repository;

import dev.vdrenkov.biblium.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ClientRepository contract.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
