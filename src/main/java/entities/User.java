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

/**
 * Class user represents entity of user of library. For example users with roles: Admin, Client
 *
 * @Author: Andrei Bychek
 */
@Entity
@Data
@NoArgsConstructor
public class User {

  /**
   * The individual number of the user
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  /**
   * The first name of the user
   */
  @Column
  private String userName;

  /**
   * The surname of the user
   */
  @Column
  private String userSurname;

  /**
   * Email of the user that used to registration or authorization to the system
   */
  @Column
  private String login;

  /**
   * Password for the registration of authorization
   */
  @Column
  private String password;

  /**
   * The role of the user that used to define of action that user can do in the system
   */
  @ManyToOne
  private Role role;

  /**
   * Represents object of the rented books cart
   * "container" of the books that user rented from the library and hasn't been returned
   */
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
