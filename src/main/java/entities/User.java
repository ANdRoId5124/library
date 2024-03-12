package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column
  private String userName;

  @Column
  private String userSurname;

  @Column
  private String login;

  @Column
  private String password;

  @ManyToOne
  private Role role;

  @OneToOne
  private RentedBookCart cart;

  public User(String userName, String userSurname, String login, String password, Role role,
      RentedBookCart cart) {
    this.userName = userName;
    this.userSurname = userSurname;
    this.login = login;
    this.password = password;
    this.role = role;
    this.cart = cart;
  }
}
