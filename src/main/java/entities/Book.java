package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bookId;

  @Column
  private String title;

  @Column
  private String author;

  public Book(String title, String author) {
    this.title = title;
    this.author = author;
  }
}
