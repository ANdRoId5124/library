package com.andrei.LibraryManager.services;

import com.andrei.LibraryManager.config.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomDetailService implements UserDetailsService {

  private final UserService USER_SERVICE;

  @Autowired
  public CustomDetailService(UserService userService) {
    this.USER_SERVICE = userService;
  }

  @Override
  public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return CustomUserDetails.of(USER_SERVICE.getUserByEmail(username).get());
  }
}
