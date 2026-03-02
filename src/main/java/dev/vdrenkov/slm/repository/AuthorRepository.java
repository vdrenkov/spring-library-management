package dev.vdrenkov.slm.repository;

import dev.vdrenkov.slm.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AuthorRepository contract.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
