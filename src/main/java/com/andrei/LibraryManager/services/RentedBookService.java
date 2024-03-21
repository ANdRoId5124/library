package com.andrei.LibraryManager.services;

import com.andrei.LibraryManager.entities.Book;
import com.andrei.LibraryManager.entities.RentedBook;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import org.springframework.stereotype.Service;
import com.andrei.LibraryManager.repositories.RentedBookRepository;

@Service
public class RentedBookService {

  private final RentedBookRepository RENTED_BOOK_REPOSITORY;
  private final RentedBookCartService RENTED_BOOK_CART_SERVICE;

  public RentedBookService(RentedBookRepository rentedBookRepository,
      RentedBookCartService rentedBookCartService) {
    RENTED_BOOK_REPOSITORY = rentedBookRepository;
    RENTED_BOOK_CART_SERVICE = rentedBookCartService;
  }

  public RentedBook addRentedBook(Book book) {
    Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    c.add(Calendar.MONTH, +3);
    RentedBook rentedBook = RentedBook.builder().book(book).
        rentalDate(new Date(System.currentTimeMillis())).rentalDate(c.getTime()).
        isReturned(false).build();
    return RENTED_BOOK_REPOSITORY.save(rentedBook);
  }

  public RentedBook updateRentedBook(RentedBook rentedBook) {
    return RENTED_BOOK_REPOSITORY.save(rentedBook);
  }

  public Optional<Set<RentedBook>> getAllUserRentedBooks(String userEmail) {
    if (RENTED_BOOK_CART_SERVICE.getRentedBookCart(userEmail).isEmpty()) {
      Optional<Set<RentedBook>> emptySet = Optional.empty();
      return emptySet;
    }
    Optional<Set<RentedBook>> rentedBooks =
        Optional.of(RENTED_BOOK_CART_SERVICE.getRentedBookCart(userEmail).get().getRentedBooks());
    return rentedBooks;
  }

  public Optional<RentedBook> getRentedBook(String userEmail, String title) {
    Optional<RentedBook> emptyBook = Optional.empty();
    if (getAllUserRentedBooks(userEmail).isEmpty()) {
      return emptyBook;
    }
    Set<RentedBook> books = getAllUserRentedBooks(userEmail).get();
    for (RentedBook rentedBook : books) {
      if (rentedBook.getBook().getTitle().equals(title)) {
        Optional<RentedBook> book = Optional.of(rentedBook);
        return book;
      }
    }
    return emptyBook;
  }

}
