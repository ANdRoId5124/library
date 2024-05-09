package com.andrei.LibraryManager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePersonalDataRequest {
  private String name;
  private String surname;
  private String email;
}
