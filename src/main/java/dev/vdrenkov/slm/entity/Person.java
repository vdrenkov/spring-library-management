package dev.vdrenkov.slm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

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


