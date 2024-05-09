package com.andrei.LibraryManager.controller;

import com.andrei.LibraryManager.dto.requests.DeleteUserRequest;
import com.andrei.LibraryManager.dto.requests.RegistrationRequest;
import com.andrei.LibraryManager.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {

  private final UserService USER_SERVICE;
  private final String ROLE_NAME = "MANAGER_ROLE";


  @PostMapping("/manager_registration")
  public ResponseEntity<?> registrate(@Valid @RequestBody RegistrationRequest dto) {
    if(USER_SERVICE.getUserByEmail(dto.getEmail()).isPresent()){
      return new ResponseEntity<>("user with that email exist", HttpStatus.CONFLICT);
    }
    return new ResponseEntity<>(USER_SERVICE.createUser(dto, ROLE_NAME), HttpStatus.OK);
  }

  @DeleteMapping("/delete_user")
  public ResponseEntity<?> deleteUser(@Valid @RequestBody DeleteUserRequest request){
    if(USER_SERVICE.getUserByEmail(request.getEmail()).isEmpty()){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    USER_SERVICE.deleteUser(
        USER_SERVICE.getUserByEmail(request.getEmail()).get()
    );
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
