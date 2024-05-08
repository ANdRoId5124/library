package com.andrei.LibraryManager.config.jwt;

import static io.jsonwebtoken.lang.Strings.hasText;

import com.andrei.LibraryManager.config.CustomUserDetails;
import com.andrei.LibraryManager.services.CustomDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class JwtFilter extends GenericFilterBean {
  private final JwtProvider provider;
  private final CustomDetailService service;
  private static final String AUTHORIZATION_HEADER = "Authorization";

  @Autowired
  public JwtFilter(JwtProvider provider, CustomDetailService service) {
    this.provider = provider;
    this.service = service;
  }


  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    String bearer = ((HttpServletRequest) servletRequest).getHeader(AUTHORIZATION_HEADER);

    String token = "";
    if(hasText(bearer) && bearer.startsWith("Bearer ")){
      token = bearer.substring(7);
    }
    if(!token.equals("") && provider.validate(token)){
      String login = provider.getLoginFromToken(token);
      CustomUserDetails userDetails = service.loadUserByUsername(login);
      UsernamePasswordAuthenticationToken auth =
          new UsernamePasswordAuthenticationToken(userDetails, null,
              userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }
}
