package com.andrei.LibraryManager.controller;

import com.andrei.LibraryManager.entities.RentedBook;
import com.andrei.LibraryManager.entities.User;
import com.andrei.LibraryManager.services.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class ClientController {

  private final UserService userService;


  @GetMapping("get_rented_books")
  public Set<RentedBook> getRentedBooks() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    User user = userService.getUserByEmail(username).get();
    return user.getCart().getRentedBooks();
  }
}
