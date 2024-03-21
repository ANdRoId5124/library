package com.andrei.LibraryManager.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class that represents the cart with books that was rented
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "rented_book_carts")
public class RentedBookCart {

  /**
   * The individual number of the cart
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rented_book_cart_id")
  private Long rentedBookCartId;


  /**
   * The container of the rented books
   */
  @OneToMany
  @JoinColumn(name = "rented_book_id")
  private Set<RentedBook> rentedBooks;

}
