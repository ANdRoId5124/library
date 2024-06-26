package com.andrei.LibraryManager.services;

import com.andrei.LibraryManager.entities.Book;
import com.andrei.LibraryManager.entities.RentedBook;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.andrei.LibraryManager.repositories.RentedBookRepository;

@Service
@RequiredArgsConstructor
public class RentedBookService {

  private final RentedBookRepository rentedBookRepository;
  private final UserService userService;


  public RentedBook addRentedBook(Book book) {
    Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    c.add(Calendar.MONTH, +3);
    RentedBook rentedBook = RentedBook.builder().book(book).
        rentalDate(new Date(System.currentTimeMillis())).returnDate(c.getTime()).
        isReturned(false).build();
    return rentedBookRepository.save(rentedBook);
  }

  public RentedBook updateRentedBook(RentedBook rentedBook) {
    return rentedBookRepository.save(rentedBook);
  }

  public Optional<Set<RentedBook>> getAllUserRentedBooks(String userEmail) {
    if(userService.getUserByEmail(userEmail).isEmpty()){
      Optional<Set<RentedBook>> emptySet = Optional.empty();
      return emptySet;
    }
    Optional<Set<RentedBook>> rentedBooks =
        Optional.of(userService.getUserByEmail(userEmail).get().getCart().getRentedBooks());
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
