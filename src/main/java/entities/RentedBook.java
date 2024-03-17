package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "rented_books")
public class RentedBook {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rented_book_id")
  private Long rentedBookId;

  @OneToOne
  @JoinColumn(name = "book_id")
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
