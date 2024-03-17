package services;

import entities.Book;
import entities.RentedBook;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;
import org.springframework.stereotype.Service;
import repositories.RentedBookRepository;

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
    RentedBook rentedBook = new RentedBook(book, new Date(System.currentTimeMillis()),
        c.getTime(), false);
    return RENTED_BOOK_REPOSITORY.save(rentedBook);
  }

  public RentedBook updateRentedBook(RentedBook rentedBook) {
    return RENTED_BOOK_REPOSITORY.save(rentedBook);
  }

  public Set<RentedBook> getAllUsersRentedBooks(String userEmail) {
    return RENTED_BOOK_CART_SERVICE.getRentedBookCart(userEmail).getRentedBooks();
  }

  public RentedBook getRentedBook(String userEmail, String title){ //Optional?? How to improve//
    Set<RentedBook> books = RENTED_BOOK_CART_SERVICE.getRentedBookCart(userEmail).getRentedBooks();
    for(RentedBook rentedBook : books){
      if (rentedBook.getBook().getTitle().equals(title)){
        return rentedBook;
      }
    }
    return null;
  }

}
