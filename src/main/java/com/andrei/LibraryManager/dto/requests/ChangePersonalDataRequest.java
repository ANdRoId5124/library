package com.andrei.LibraryManager.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePersonalDataRequest {
  @Pattern(message = "First name is not valid", regexp = "^[A-Za-z]{2,25}$")
  private String name;

  @Pattern(message = "Last name is not valid", regexp = "^[a-zA-Z'-]{2,25}$")
  private String surname;

  @Email(
      message = "Email is not valid",
      regexp =
          "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
              + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
  private String email;
}
