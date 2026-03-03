package dev.vdrenkov.biblium.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

/**
 * Book component.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Author author;

    @Column(name = "quantity")
    private int quantity;

    /**
     * Handles Book operation.
     *
     * @param name
     *     Name value.
     * @param publishDate
     *     Book publish date.
     * @param author
     *     Author entity value.
     * @param quantity
     *     Quantity value.
     */
    public Book(String name, LocalDate publishDate, Author author, int quantity) {
        this.name = name;
        this.publishDate = publishDate;
        this.author = author;
        this.quantity = quantity;
    }
}
