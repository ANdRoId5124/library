package com.andrei.LibraryManager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@AllArgsConstructor
@Builder
@Table(name = "users",
    uniqueConstraints = {@UniqueConstraint(name = "unique_c_email", columnNames = "email")})
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
  @Size(max = 25)
  private String userName;

  /**
   * The surname of the user
   */
  @Column
  @Size(max = 25)
  private String userSurname;


  /**
   * Email of the user that used to registration or authorization to the system
   */

  @NotBlank
  @Size(max = 30)
  @Email
  private String email;

  /**
   * Password for the registration of authorization
   */
  @NotBlank
  @Size(max = 150)
  @JsonIgnore
  @Column
  private String password;

  /**
   * The role of the user that used to define of action that user can do in the system
   */
  @ManyToOne
  @JoinColumn(name = "role_id")
  private Role role;

  /**
   * Represents object of the rented books cart "container" of the books that user rented from the
   * library and hasn't been returned
   */
  @OneToOne
  @JoinColumn(name = "rented_book_cart_id")
  private RentedBookCart cart;
}
