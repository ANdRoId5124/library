package com.andrei.LibraryManager.services;

import com.andrei.LibraryManager.dto.RegistrationDto;
import com.andrei.LibraryManager.entities.RentedBook;
import com.andrei.LibraryManager.entities.RentedBookCart;
import com.andrei.LibraryManager.entities.User;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.andrei.LibraryManager.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository USER_REPOSITORY;
  private final RoleService ROLE_SERVICE;
  private final RentedBookCartService RENTED_BOOK_CART_SERVICE;
  private final PasswordEncoder PASSWORD_ENCODER;


  public User createUser(RegistrationDto dto, String roleName) {
    if(getUserByEmail(dto.getEmail()).isPresent()){
      throw new IllegalArgumentException("User with that email already exist");
    }
    if(ROLE_SERVICE.getRoleByName(roleName).isEmpty()){
      throw new EntityNotFoundException("Role with name " + roleName +" not found");
    }
    User user = User.builder().userName(dto.getName()).userSurname(dto.getSurname())
            .email(dto.getEmail()).password(PASSWORD_ENCODER.encode(dto.getPassword()))
            .cart(RENTED_BOOK_CART_SERVICE.addRentedBookCart())
        .role(ROLE_SERVICE.getRoleByName(roleName).get()).build();
    return USER_REPOSITORY.save(user);
  }

  public User updateUser(User user) {
    return USER_REPOSITORY.save(user);
  }

  public void deleteUser(User user) {
    USER_REPOSITORY.delete(user);
  }

  public Optional<User> getUserByEmail(String email) {
    return USER_REPOSITORY.findUserByEmail(email);
  }

  public Optional<User> getUserByEmailAndPassword(String email, String password) {
    Optional<User> user = USER_REPOSITORY.findUserByEmail(email);
    if (user.isPresent() && PASSWORD_ENCODER.matches(password, user.get().getPassword())) {
      return user;
    }
    return Optional.empty();
  }
}


