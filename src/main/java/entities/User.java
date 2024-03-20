package entities;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users",
    uniqueConstraints = {@UniqueConstraint(name = "unique_c_email", columnNames = "email")})
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column
  private String userName;

  @Column
  private String userSurname;

  @Column(nullable = false)
  private String email;

  @Column
  private String password;

  @ManyToOne
  @JoinColumn(name = "role_id")
  private Role role;

  @OneToOne
  @JoinColumn(name = "rented_book_cart_id")
  private RentedBookCart cart;
}
