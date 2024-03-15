package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class that represents the book that was rented
 *
 * @Author: Andrei Bychek
 */
@Entity
@Data
@NoArgsConstructor
public class RentedBook {

  /**
   * The individual number of the object that represents rented book
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long rentedBookId;

  /**
   * The object of the book that is in the library
   */
  @OneToOne
  private Book book;

  /**
   * The date when book was rented
   */
  @Column
  private Date rentalDate;

  /**
   * The date when book must be returned
   */
  @Column
  private Date returnDate;

  /**
   * The variable that represents is book was returned
   */
  @Column
  private boolean isReturned;

  public RentedBook(Book book, Date rentalDate, Date returnDate, boolean isReturned) {
    this.book = book;
    this.rentalDate = rentalDate;
    this.returnDate = returnDate;
    this.isReturned = isReturned;
  }
}
