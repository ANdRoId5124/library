package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class that represents a book that is in the library
 *
 * @Author: Andrei Bychek
 */
@Entity
@Data
@NoArgsConstructor
public class Book {

  /**
   * The individual number of the book
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bookId;

  /**
   * The title(name) of the book
   */
  @Column
  private String title;

  /**
   * The name and the last name of the author
   */
  @Column
  private String author;

  public Book(String title, String author) {
    this.title = title;
    this.author = author;
  }
}
