package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "rented_book_carts")
public class RentedBookCart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rented_book_cart_id")
  private Long rentedBookCartId;


  @OneToMany
  @JoinColumn(name = "rented_book_id")
  private Set<RentedBook> rentedBooks;

  public RentedBookCart(Set<RentedBook> rentedBooks) {
    this.rentedBooks = rentedBooks;
  }
}
