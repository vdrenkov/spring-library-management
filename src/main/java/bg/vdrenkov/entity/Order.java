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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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

  @ManyToMany(cascade = CascadeType.REMOVE)
  @JoinTable(name = "ORDER_BOOKS",
             joinColumns = @JoinColumn(name = "ORDER_ID"),
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