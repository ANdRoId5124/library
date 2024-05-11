package com.andrei.LibraryManager.services;

import com.andrei.LibraryManager.entities.RentedBookCart;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.andrei.LibraryManager.repositories.RentedBookCartRepository;

@Service
@RequiredArgsConstructor
public class RentedBookCartService {

  private final RentedBookCartRepository rentedBookCartRepository;


  public RentedBookCart addRentedBookCart() {
    RentedBookCart cart = RentedBookCart.builder().rentedBooks(new HashSet<>())
        .build();
    return rentedBookCartRepository.save(cart);
  }

  public void deleteRentedBookCart(RentedBookCart cart) {
    rentedBookCartRepository.delete(cart);
  }

  public RentedBookCart updateRentedBookCart(RentedBookCart cart) {
    return rentedBookCartRepository.save(cart);
  }
}
