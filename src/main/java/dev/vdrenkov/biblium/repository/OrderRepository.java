package dev.vdrenkov.biblium.repository;

import dev.vdrenkov.biblium.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * OrderRepository contract.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    /**
     * Handles findByClientId operation.
     *
     * @param clientId
     *     Identifier of the target entity.
     * @return List of orders.
     */
    List<Order> findByClientId(int clientId);

    /**
     * Handles findByIssueDate operation.
     *
     * @param date
     *     Date value.
     * @return List of orders.
     */
    List<Order> findByIssueDate(LocalDate date);

    /**
     * Handles findByIssueDateBefore operation.
     *
     * @param date
     *     Date value.
     * @return List of orders.
     */
    List<Order> findByIssueDateBefore(LocalDate date);

    /**
     * Handles findByIssueDateAfter operation.
     *
     * @param date
     *     Date value.
     * @return List of orders.
     */
    List<Order> findByIssueDateAfter(LocalDate date);

    /**
     * Handles findByDueDate operation.
     *
     * @param date
     *     Date value.
     * @return List of orders.
     */
    List<Order> findByDueDate(LocalDate date);

    /**
     * Handles findByDueDateBefore operation.
     *
     * @param date
     *     Date value.
     * @return List of orders.
     */
    List<Order> findByDueDateBefore(LocalDate date);

    /**
     * Handles findByDueDateAfter operation.
     *
     * @param date
     *     Date value.
     * @return List of orders.
     */
    List<Order> findByDueDateAfter(LocalDate date);
}
