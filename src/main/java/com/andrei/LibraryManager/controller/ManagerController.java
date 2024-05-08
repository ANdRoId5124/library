package com.andrei.LibraryManager.controller;

import com.andrei.LibraryManager.dto.BookDto;
import com.andrei.LibraryManager.entities.Book;
import com.andrei.LibraryManager.entities.RentedBook;
import com.andrei.LibraryManager.entities.User;
import com.andrei.LibraryManager.services.BookService;
import com.andrei.LibraryManager.services.RentedBookCartService;
import com.andrei.LibraryManager.services.RentedBookService;
import com.andrei.LibraryManager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {

  private final UserService USER_SERVICE;
  private final BookService BOOK_SERVICE;
  private final RentedBookService RENTED_BOOK_SERVICE;
  private final RentedBookCartService RENTED_BOOK_CART_SERVICE;

  @PostMapping("rent_book")
  public ResponseEntity<?> rent(@RequestParam(name = "email") String email,
      @RequestParam(name = "title") String title, @RequestParam(name = "author") String author) {
    if (USER_SERVICE.getUserByEmail(email).isEmpty()) {
      return new ResponseEntity<>("Can't find client with email " + email,
          HttpStatus.NOT_FOUND);
    }
    if (BOOK_SERVICE.getBookByTitleAndByAuthor(title, author).isEmpty()) {
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
            BOOK_SERVICE.getBookByTitleAndByAuthor(title, author).get()));
    return new ResponseEntity<>(RENTED_BOOK_CART_SERVICE.updateRentedBookCart(user.getCart()),
        HttpStatus.OK);
  }

  @PutMapping("return_book")
  public ResponseEntity<?> return_a_book(@RequestParam(name = "email") String email,
      @RequestParam(name = "book_title") String title) {
    if (RENTED_BOOK_SERVICE.getRentedBook(email, title).isEmpty()) {
      return new ResponseEntity<>("User or book not found", HttpStatus.NOT_FOUND);
    }
    RentedBook book = RENTED_BOOK_SERVICE.getRentedBook(email, title).get();
    book.setReturned(true);
    return new ResponseEntity<>(RENTED_BOOK_SERVICE.updateRentedBook(book), HttpStatus.OK);
  }

  @PostMapping("add_book_to_library")
  public ResponseEntity<?> addBookToLibrary(@RequestBody BookDto dto) {
    if (BOOK_SERVICE.getBookByTitleAndByAuthor(dto.getTitle(), dto.getAuthor()).isPresent()) {
      return new ResponseEntity<>("Book already exist", HttpStatus.CONFLICT);
    }
    return new ResponseEntity<>(BOOK_SERVICE.addBook(Book.builder().title(dto.getTitle()).
        author(dto.getAuthor()).build()), HttpStatus.OK);
  }

  @DeleteMapping("delete_book_from_library")
  public ResponseEntity<?> deleteBookFromLibrary(@RequestBody BookDto dto) {
    if (BOOK_SERVICE.getBookByTitleAndByAuthor(dto.getTitle(), dto.getAuthor()).isEmpty()) {
      return new ResponseEntity<>("Cannot find a book", HttpStatus.NOT_FOUND);
    }
    BOOK_SERVICE.deleteBook(BOOK_SERVICE.getBookByTitleAndByAuthor(dto.getTitle(), dto.getAuthor()).
        get());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
