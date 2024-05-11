package com.andrei.LibraryManager.controller;

import com.andrei.LibraryManager.dto.BookDto;
import com.andrei.LibraryManager.dto.requests.RentBookRequest;
import com.andrei.LibraryManager.entities.Book;
import com.andrei.LibraryManager.entities.RentedBook;
import com.andrei.LibraryManager.entities.User;
import com.andrei.LibraryManager.services.BookService;
import com.andrei.LibraryManager.services.RentedBookCartService;
import com.andrei.LibraryManager.services.RentedBookService;
import com.andrei.LibraryManager.services.UserService;
import jakarta.validation.Valid;
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

  private final UserService userService;
  private final BookService bookService;
  private final RentedBookService rentedBookService;
  private final RentedBookCartService rentedBookCartService;

  @PostMapping("rent_book")
  public ResponseEntity<?> rent(@Valid @RequestBody RentBookRequest request) {
    if (userService.getUserByEmail(request.getEmail()).isEmpty()) {
      return new ResponseEntity<>("Can't find client with email " + request.getEmail(),
          HttpStatus.NOT_FOUND);
    }
    if (bookService.getBookByTitleAndByAuthor(request.getTitle(), request.getAuthor()).isEmpty()) {
      return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
    }
    if (rentedBookService.getRentedBook(request.getEmail(), request.getTitle()).isPresent()
        && !rentedBookService.getRentedBook(request.getEmail(), request.getTitle()).get().isReturned()) {
      return new ResponseEntity<>("Sorry you already rented book",
          HttpStatus.CONFLICT);
    }
    User user = userService.getUserByEmail(request.getEmail()).get();
    user.getCart().getRentedBooks().add(
        rentedBookService.addRentedBook(
            bookService.getBookByTitleAndByAuthor(request.getTitle(), request.getAuthor()).get()));
    return new ResponseEntity<>(rentedBookCartService.updateRentedBookCart(user.getCart()),
        HttpStatus.OK);
  }

  @PutMapping("return_book")
  public ResponseEntity<?> return_a_book(@RequestParam(name = "email") String email,
      @RequestParam(name = "book_title") String title) {
    if (rentedBookService.getRentedBook(email, title).isEmpty()) {
      return new ResponseEntity<>("User or book not found", HttpStatus.NOT_FOUND);
    }
    RentedBook book = rentedBookService.getRentedBook(email, title).get();
    book.setReturned(true);
    return new ResponseEntity<>(rentedBookService.updateRentedBook(book), HttpStatus.OK);
  }

  @PostMapping("add_book_to_library")
  public ResponseEntity<?> addBookToLibrary(@RequestBody BookDto dto) {
    if (bookService.getBookByTitleAndByAuthor(dto.getTitle(), dto.getAuthor()).isPresent()) {
      return new ResponseEntity<>("Book already exist", HttpStatus.CONFLICT);
    }
    return new ResponseEntity<>(bookService.addBook(Book.builder().title(dto.getTitle()).
        author(dto.getAuthor()).build()), HttpStatus.OK);
  }

  @DeleteMapping("delete_book_from_library")
  public ResponseEntity<?> deleteBookFromLibrary(@RequestBody BookDto dto) {
    if (bookService.getBookByTitleAndByAuthor(dto.getTitle(), dto.getAuthor()).isEmpty()) {
      return new ResponseEntity<>("Cannot find a book", HttpStatus.NOT_FOUND);
    }
    bookService.deleteBook(bookService.getBookByTitleAndByAuthor(dto.getTitle(), dto.getAuthor()).
        get());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
