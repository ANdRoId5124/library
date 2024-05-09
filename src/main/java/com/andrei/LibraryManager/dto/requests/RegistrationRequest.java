package com.andrei.LibraryManager.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

  @NotNull(message = "First name is should not be null")
  @Pattern(message = "First name is not valid", regexp = "^[A-Za-zЁёА-я]{2,25}$")
  private String name;

  @Pattern(message = "Last name is not valid", regexp = "^[a-zA-ZА-Яа-я'-]{2,25}$")
  private String surname;

  @NotNull(message = "Email is should not be null")
  @Email(
      message = "Email is not valid",
      regexp =
          "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
              + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
  private String email;

  @NotNull(message = "Password is should not be null")
  @Pattern(message = "Password is not valid",
      regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>])*.{6,50}$")
  private String password;
}
