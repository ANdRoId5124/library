package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class that represents the cart with books that was rented
 */
@Entity
@Data
@NoArgsConstructor
public class RentedBookCart {

  /**
   * The individual number of the cart
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bookCartId;


  /**
   * The container of the rented books
   */
  @OneToMany
  private Set<RentedBook> rentedBooks;

  public RentedBookCart(Set<RentedBook> rentedBooks) {
    this.rentedBooks = rentedBooks;
  }
}
