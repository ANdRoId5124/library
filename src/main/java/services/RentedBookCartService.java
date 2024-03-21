package services;

import entities.RentedBookCart;
import entities.User;
import java.util.HashSet;
import java.util.Optional;
import org.springframework.stereotype.Service;
import repositories.RentedBookCartRepository;

@Service
public class RentedBookCartService {

  private final RentedBookCartRepository RENTED_BOOK_CART_REPOSITORY;
  private final UserService USER_SERVICE;

  public RentedBookCartService(RentedBookCartRepository rentedBookCartRepository,
      UserService userService) {
    RENTED_BOOK_CART_REPOSITORY = rentedBookCartRepository;
    USER_SERVICE = userService;
  }

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

  public Optional<RentedBookCart> getRentedBookCart(String userEmail) {
    if (USER_SERVICE.getUserByEmail(userEmail).isEmpty()) {
      Optional<RentedBookCart> emptyCart = Optional.empty();
      return emptyCart;
    }
    Optional<RentedBookCart> rentedBookCart = Optional.of(
        USER_SERVICE.getUserByEmail(userEmail).get().getCart());
    return rentedBookCart;
  }
}
