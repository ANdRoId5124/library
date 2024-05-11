package com.andrei.LibraryManager.controller;


import com.andrei.LibraryManager.config.jwt.JwtProvider;
import com.andrei.LibraryManager.dto.requests.AuthRequest;
import com.andrei.LibraryManager.dto.AuthResponse;
import com.andrei.LibraryManager.dto.requests.IntrospectRequest;
import com.andrei.LibraryManager.dto.requests.RegistrationRequest;
import com.andrei.LibraryManager.entities.User;
import com.andrei.LibraryManager.services.UserService;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private final UserService userService;
  private final JwtProvider provider;
  private final String roleName = "CLIENT_ROLE";


  @PostMapping("/registration")
  public ResponseEntity<?> registrate(@Valid @RequestBody RegistrationRequest dto) {
    if (userService.getUserByEmail(dto.getEmail()).isPresent()) {
      return new ResponseEntity<>("user with that email exist", HttpStatus.CONFLICT);
    }
    return new ResponseEntity<>(userService.createUser(dto, roleName), HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest dto) {
    Optional<User> userOptional = userService.getUserByEmailAndPassword(dto.getEmail(),
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
