package bg.vdrenkov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
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
