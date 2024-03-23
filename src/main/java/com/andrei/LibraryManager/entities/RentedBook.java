package com.andrei.LibraryManager.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@AllArgsConstructor
@Builder
@Table(name = "rented_books")
public class RentedBook {

  /**
   * The individual number of the object that represents rented book
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rented_book_id")
  private Long rentedBookId;

  /**
   * The object of the book that is in the library
   */
  @ManyToOne
  @JoinColumn(name = "book_id")
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RentedBook that = (RentedBook) o;
    return Objects.equals(book, that.book);
  }

  @Override
  public int hashCode() {
    return Objects.hash(book);
  }
}
