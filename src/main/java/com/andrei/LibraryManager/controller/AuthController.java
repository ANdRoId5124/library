package com.andrei.LibraryManager.controller;


import com.andrei.LibraryManager.config.jwt.JwtProvider;
import com.andrei.LibraryManager.dto.AuthDto;
import com.andrei.LibraryManager.dto.AuthResponse;
import com.andrei.LibraryManager.dto.IntrospectRequest;
import com.andrei.LibraryManager.dto.RegistrationDto;
import com.andrei.LibraryManager.entities.User;
import com.andrei.LibraryManager.services.RentedBookCartService;
import com.andrei.LibraryManager.services.RoleService;
import com.andrei.LibraryManager.services.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

  private final UserService USER_SERVICE;
  private final RentedBookCartService RENTED_BOOK_CART_SERVICE;
  private final RoleService ROLE_SERVICE;

  private final JwtProvider provider;


  @Autowired
  public AuthController(UserService userService, RentedBookCartService rentedBookCartService,
      RoleService roleService, JwtProvider provider) {
    USER_SERVICE = userService;
    RENTED_BOOK_CART_SERVICE = rentedBookCartService;
    ROLE_SERVICE = roleService;
    this.provider = provider;
  }

  @PostMapping("/registration")
  public ResponseEntity<?> registrate(@RequestBody RegistrationDto dto) {
    if (USER_SERVICE.getUserByEmail(dto.getEmail()).isPresent()) {
      return new ResponseEntity<>("User with email " +
          dto.getEmail() + " already exist", HttpStatus.CONFLICT);
    }
    if (ROLE_SERVICE.getRoleByName("CLIENT_ROLE").isEmpty()) {
      return new ResponseEntity<>("Role not found", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(USER_SERVICE.createUser(User.builder().userName(dto.getName()).
        userSurname(dto.getSurname()).email(dto.getEmail()).password(dto.getPassword()).role(
            ROLE_SERVICE.getRoleByName("CLIENT_ROLE").get()).
        cart(RENTED_BOOK_CART_SERVICE.addRentedBookCart()).build()), HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody AuthDto dto) {
    Optional<User> userOptional = USER_SERVICE.getUserByEmailAndPassword(dto.getEmail(),
        dto.getPassword());

    if (userOptional.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    String accessToken = provider.generatedAccessToken(dto.getEmail());
    String refreshToken = provider.generateRefreshToken(dto.getEmail());

    return new ResponseEntity<>(new AuthResponse(accessToken, refreshToken),
        HttpStatus.OK);
  }

  @PostMapping("/introspect")
  public ResponseEntity<AuthResponse> introspect(@RequestBody IntrospectRequest request) {
    boolean isValid = provider.validate(request.getRefreshToken());
    if (isValid) {
      String login = provider.getLoginFromToken(request.getRefreshToken());
      return getAuth(login);
    }
    return new ResponseEntity<>(
        HttpStatus.FORBIDDEN);
  }

  private ResponseEntity<AuthResponse> getAuth(String login) {
    String accessToken = provider.generatedAccessToken(login);
    String refreshToken = provider.generateRefreshToken(login);

    return new ResponseEntity<>(new AuthResponse(accessToken, refreshToken),
        HttpStatus.OK);
  }
}
