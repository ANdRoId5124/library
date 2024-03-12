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

@Entity
@Data
@NoArgsConstructor
public class RentedBook {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long rentedBookId;

  @OneToOne
  private Book book;

  @Column
  private Date rentalDate;

  @Column
  private Date returnDate;

  @Column
  private boolean isReturned;

  public RentedBook(Book book, Date rentalDate, Date returnDate, boolean isReturned) {
    this.book = book;
    this.rentalDate = rentalDate;
    this.returnDate = returnDate;
    this.isReturned = isReturned;
  }
}
