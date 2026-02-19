package bg.vdrenkov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "USERS")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private int id;

  @Column(name = "USERNAME", unique = true)
  private String username;

  @Column(name = "PASSWORD")
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "USERS_ROLES",
             joinColumns = @JoinColumn(name = "USER_ID"),
             inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
  private List<UserRole> userRoles;

  public User(String username, String password, List<UserRole> userRoles) {
    this.username = username;
    this.password = password;
    this.userRoles = userRoles;
  }
}


