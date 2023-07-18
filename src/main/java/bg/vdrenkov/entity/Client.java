package bg.vdrenkov.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "CLIENTS")
public class Client extends Person {

  @Column(name = "PHONE_NUMBER", unique = true)
  private String phoneNumber;

  @Column(name = "EMAIL", unique = true)
  private String email;

  public Client(int id, String name, String surname, String phoneNumber, String email) {
    super(id, name, surname);
    this.phoneNumber = phoneNumber;
    this.email = email;
  }

  public Client(String name, String surname, String phoneNumber, String email) {
    super(name, surname);
    this.phoneNumber = phoneNumber;
    this.email = email;
  }
}
