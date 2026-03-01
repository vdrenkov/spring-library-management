package dev.vdrenkov.slm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

/**
 * Order component.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client client;

    @ManyToMany
    @JoinTable(name = "ORDER_BOOKS", joinColumns = @JoinColumn(name = "ORDER_ID"),
        inverseJoinColumns = @JoinColumn(name = "BOOK_ID"))
    private List<Book> books;

    @Column(name = "ISSUE_DATE")
    private LocalDate issueDate;

    @Column(name = "DUE_DATE")
    private LocalDate dueDate;

    /**
     * Handles Order operation.
     *
     * @param client
     *     Client entity value.
     * @param books
     *     Books included in the order.
     * @param issueDate
     *     Order issue date.
     * @param dueDate
     *     Order due date.
     */
    public Order(final Client client, final List<Book> books, final LocalDate issueDate, final LocalDate dueDate) {
        this.client = client;
        this.books = books;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }
}
