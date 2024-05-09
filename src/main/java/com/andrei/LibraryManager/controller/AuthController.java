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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

  private final UserService USER_SERVICE;
  private final JwtProvider PROVIDER;
  private final String ROLE_NAME = "CLIENT_ROLE";


  @PostMapping("/registration")
  public ResponseEntity<?> registrate(@RequestBody RegistrationDto dto) {
    if(USER_SERVICE.getUserByEmail(dto.getEmail()).isPresent()){
      return new ResponseEntity<>("user with that email exist", HttpStatus.CONFLICT);
    }
    return new ResponseEntity<>(USER_SERVICE.createUser(dto, ROLE_NAME), HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody AuthDto dto) {
    Optional<User> userOptional = USER_SERVICE.getUserByEmailAndPassword(dto.getEmail(),
        dto.getPassword());

    if (userOptional.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    String accessToken = PROVIDER.generatedAccessToken(dto.getEmail());
    String refreshToken = PROVIDER.generateRefreshToken(dto.getEmail());

    return new ResponseEntity<>(new AuthResponse(accessToken, refreshToken),
        HttpStatus.OK);
  }

  @PostMapping("/introspect")
  public ResponseEntity<AuthResponse> introspect(@RequestBody IntrospectRequest request) {
    boolean isValid = PROVIDER.validate(request.getRefreshToken());
    if (isValid) {
      String login = PROVIDER.getLoginFromToken(request.getRefreshToken());
      return getAuth(login);
    }
    return new ResponseEntity<>(
        HttpStatus.FORBIDDEN);
  }

  private ResponseEntity<AuthResponse> getAuth(String login) {
    String accessToken = PROVIDER.generatedAccessToken(login);
    String refreshToken = PROVIDER.generateRefreshToken(login);

    return new ResponseEntity<>(new AuthResponse(accessToken, refreshToken),
        HttpStatus.OK);
  }
}
