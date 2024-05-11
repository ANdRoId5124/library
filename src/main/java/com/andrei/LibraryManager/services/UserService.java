package com.andrei.LibraryManager.services;

import com.andrei.LibraryManager.dto.requests.RegistrationRequest;
import com.andrei.LibraryManager.entities.User;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.andrei.LibraryManager.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final RoleService roleService;
  private final RentedBookCartService rentedBookCartService;
  private final PasswordEncoder passwordEncoder;


  public User createUser(RegistrationRequest dto, String roleName) {
    if (getUserByEmail(dto.getEmail()).isPresent()) {
      throw new IllegalArgumentException("User with that email already exist");
    }
    if (roleService.getRoleByName(roleName).isEmpty()) {
      throw new EntityNotFoundException("Role with name " + roleName + " not found");
    }
    User user = User.builder().userName(dto.getName()).userSurname(dto.getSurname())
        .email(dto.getEmail()).password(passwordEncoder.encode(dto.getPassword()))
        .cart(rentedBookCartService.addRentedBookCart())
        .role(roleService.getRoleByName(roleName).get()).build();
    return userRepository.save(user);
  }

  public User updateUser(User user) {
    return userRepository.save(user);
  }

  public void deleteUser(User user) {
    userRepository.delete(user);
  }

  public Optional<User> getUserByEmail(String email) {
    return userRepository.findUserByEmail(email);
  }

  public Optional<User> getUserByEmailAndPassword(String email, String password) {
    Optional<User> user = userRepository.findUserByEmail(email);
    if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
      return user;
    }
    return Optional.empty();
  }
}


