package com.andrei.LibraryManager.services;

import com.andrei.LibraryManager.entities.User;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.andrei.LibraryManager.repositories.UserRepository;

@Service
public class UserService {

  private final UserRepository USER_REPOSITORY;

  public UserService(UserRepository userRepository) {
    this.USER_REPOSITORY = userRepository;
  }

  public User createUser(User user) {
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

}
