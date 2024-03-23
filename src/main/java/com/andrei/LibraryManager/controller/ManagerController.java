package com.andrei.LibraryManager.controller;

import com.andrei.LibraryManager.entities.User;
import com.andrei.LibraryManager.services.BookService;
import com.andrei.LibraryManager.services.RentedBookCartService;
import com.andrei.LibraryManager.services.RentedBookService;
import com.andrei.LibraryManager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("manager")
public class ManagerController {

  private final UserService USER_SERVICE;
  private final BookService BOOK_SERVICE;
  private final RentedBookService RENTED_BOOK_SERVICE;
  private final RentedBookCartService RENTED_BOOK_CART_SERVICE;

  @Autowired
  public ManagerController(UserService userService, BookService bookService,
      RentedBookService rentedBookService, RentedBookCartService r) {
    USER_SERVICE = userService;
    BOOK_SERVICE = bookService;
    RENTED_BOOK_SERVICE = rentedBookService;
    this.RENTED_BOOK_CART_SERVICE = r;
  }

  @PostMapping("rentBook")
  public ResponseEntity<?> rent(@RequestParam(name = "email") String email,
      @RequestParam(name = "title") String title) {
    if (USER_SERVICE.getUserByEmail(email).isEmpty()) {
      return new ResponseEntity<>("Can't find client with email " + email,
          HttpStatus.NOT_FOUND);
    }
    if (BOOK_SERVICE.getBookByTitle((title)).isEmpty()) {
      return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
    }
    if (RENTED_BOOK_SERVICE.getRentedBook(email, title).isPresent()
        && !RENTED_BOOK_SERVICE.getRentedBook(email, title).get().isReturned()) {
      return new ResponseEntity<>("Sorry you already rented book",
          HttpStatus.CONFLICT);
    }
    User user = USER_SERVICE.getUserByEmail(email).get();
    user.getCart().getRentedBooks().add(
        RENTED_BOOK_SERVICE.addRentedBook(
            BOOK_SERVICE.getBookByTitle((title)).get()));
    return new ResponseEntity<>(RENTED_BOOK_CART_SERVICE.updateRentedBookCart(user.getCart()), HttpStatus.OK);
  }
}
