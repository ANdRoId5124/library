package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class RentedBookCart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bookCartId;


  @OneToMany
  private Set<RentedBook> rentedBooks;

  public RentedBookCart(Set<RentedBook> rentedBooks) {
    this.rentedBooks = rentedBooks;
  }
}