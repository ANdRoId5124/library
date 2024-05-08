package com.andrei.LibraryManager.services;

import com.andrei.LibraryManager.entities.User;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.andrei.LibraryManager.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository USER_REPOSITORY;

  private final PasswordEncoder PASSWORD_ENCODER;


  public User createUser(User user) {
    user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
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


