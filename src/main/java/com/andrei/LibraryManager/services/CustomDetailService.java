package com.andrei.LibraryManager.services;

import com.andrei.LibraryManager.config.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomDetailService implements UserDetailsService {

  private final UserService userService;


  @Override
  public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return CustomUserDetails.of(userService.getUserByEmail(username).get());
  }
}
