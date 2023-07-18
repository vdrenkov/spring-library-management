package bg.vdrenkov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "BOOKS")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private int id;

  @Column(name = "NAME", unique = true)
  private String name;

  @Column(name = "PUBLISH_DATE")
  private LocalDate publishDate;

  @ManyToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "AUTHOR_ID")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Author author;

  @Column(name = "QUANTITY")
  private int quantity;

  public Book(String name, LocalDate publishDate, Author author, int quantity) {
    this.name = name;
    this.publishDate = publishDate;
    this.author = author;
    this.quantity = quantity;
  }
}
