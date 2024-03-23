package com.andrei.LibraryManager.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@AllArgsConstructor
@Builder
@Table(name = "books")
public class Book {

  /**
   * The individual number of the book
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "book_id")
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Book book = (Book) o;
    return Objects.equals(title, book.title) && Objects.equals(author,
        book.author);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, author);
  }
}
