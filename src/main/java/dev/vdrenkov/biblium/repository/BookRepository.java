package dev.vdrenkov.biblium.repository;

import dev.vdrenkov.biblium.entity.Book;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * BookRepository contract.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    /**
     * Handles findByQuantityGreaterThan operation.
     *
     * @param quantity
     *     Quantity value.
     * @return List of books.
     */
    List<Book> findByQuantityGreaterThan(int quantity);

    /**
     * Handles findByAuthorIdAndQuantityGreaterThan operation.
     *
     * @param authorId
     *     Identifier of the target entity.
     * @param quantity
     *     Quantity value.
     * @return List of books.
     */
    List<Book> findByAuthorIdAndQuantityGreaterThan(int authorId, int quantity);

    /**
     * Handles findByIdForUpdate operation.
     *
     * @param id
     *     Identifier of the target entity.
     * @return Optional containing the requested book.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Book b WHERE b.id = :id")
    Optional<Book> findByIdForUpdate(@Param("id") int id);
}
