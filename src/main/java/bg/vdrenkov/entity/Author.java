package bg.vdrenkov.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@NoArgsConstructor
@Entity
@Table(name = "AUTHORS", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "surname"}))
public class Author extends Person {

  public Author(int id, String name, String surname) {
    super(id, name, surname);
  }

  public Author(String name, String surname) {
    super(name, surname);
  }
}