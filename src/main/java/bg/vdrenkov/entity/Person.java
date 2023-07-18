package bg.vdrenkov.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@MappedSuperclass
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  protected int id;

  @Column(name = "NAME")
  protected String name;

  @Column(name = "SURNAME")
  protected String surname;

  public Person(String name, String surname) {
    this.name = name;
    this.surname = surname;
  }
}
