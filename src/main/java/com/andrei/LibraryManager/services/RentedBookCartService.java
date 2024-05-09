package com.andrei.LibraryManager.services;

import com.andrei.LibraryManager.entities.RentedBookCart;
import java.util.HashSet;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.andrei.LibraryManager.repositories.RentedBookCartRepository;

@Service
@RequiredArgsConstructor
public class RentedBookCartService {

  private final RentedBookCartRepository RENTED_BOOK_CART_REPOSITORY;


  public RentedBookCart addRentedBookCart() {
    RentedBookCart cart = RentedBookCart.builder().rentedBooks(new HashSet<>())
        .build();
    return RENTED_BOOK_CART_REPOSITORY.save(cart);
  }

  public void deleteRentedBookCart(RentedBookCart cart) {
    RENTED_BOOK_CART_REPOSITORY.delete(cart);
  }

  public RentedBookCart updateRentedBookCart(RentedBookCart cart) {
    return RENTED_BOOK_CART_REPOSITORY.save(cart);
  }
}
