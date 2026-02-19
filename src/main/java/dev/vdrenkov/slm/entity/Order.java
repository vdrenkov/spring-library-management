package dev.vdrenkov.slm.entity;

import jakarta.persistence.CascadeType;
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

    @ManyToOne(cascade = CascadeType.REMOVE)
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

    public Order(Client client, List<Book> books, LocalDate issueDate, LocalDate dueDate) {
        this.client = client;
        this.books = books;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }
}

