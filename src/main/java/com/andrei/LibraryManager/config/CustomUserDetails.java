package com.andrei.LibraryManager.config;

import com.andrei.LibraryManager.entities.User;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

  private String email;

  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  public static CustomUserDetails of(User user){
    CustomUserDetails details = new CustomUserDetails();
    details.email = user.getEmail();
    details.password = user.getPassword();
    details.authorities = Collections.singletonList(
        new SimpleGrantedAuthority(user.getRole().getRoleName())
    );
    return details;
  }
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
