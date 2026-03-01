package dev.vdrenkov.slm.repository;

import dev.vdrenkov.slm.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
/**
 * AuthorRepository contract.
 */
public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
