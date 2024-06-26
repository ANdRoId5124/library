package com.andrei.LibraryManager.controller;

import com.andrei.LibraryManager.dto.requests.ChangePasswordRequest;
import com.andrei.LibraryManager.dto.requests.ChangePersonalDataRequest;
import com.andrei.LibraryManager.entities.RentedBook;
import com.andrei.LibraryManager.entities.User;
import com.andrei.LibraryManager.services.UserService;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class ClientController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @GetMapping("get_rented_books")
  public Set<RentedBook> getRentedBooks() {
    String username = SecurityContextHolder.getContext().getAuthentication()
        .getName();
    User user = userService.getUserByEmail(username).get();
    return user.getCart().getRentedBooks();
  }

  @PutMapping("change_password")
  public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
    String username = SecurityContextHolder.getContext().getAuthentication()
        .getName();
    if (!passwordEncoder.matches(request.getOldPassword(),
        userService.getUserByEmail(username).get().getPassword())) {
      return new ResponseEntity<>("Wrong old password", HttpStatus.BAD_REQUEST);
    }
    User user = userService.getUserByEmail(username).get();
    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    userService.updateUser(user);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("change_personal_data")
  public ResponseEntity<?> changePersonalData(@Valid @RequestBody ChangePersonalDataRequest request) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userService.getUserByEmail(username).get();
    user.setUserName(request.getName());
    user.setEmail(request.getEmail());
    user.setUserSurname(request.getSurname());
    userService.updateUser(user);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
