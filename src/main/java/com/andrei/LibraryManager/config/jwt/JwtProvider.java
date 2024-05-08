package com.andrei.LibraryManager.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

  @Value("${jwt.secret}")
  private String jwtSecret;

  public String generatedAccessToken(String login){
    return generateToken(login, TimeUnit.MINUTES.toMillis(60));
  }

  public String generateRefreshToken(String login){
    return generateToken(login, TimeUnit.DAYS.toMillis(1));
  }

  private String generateToken(String login, long expirationTime){
    return Jwts.builder()
        .setSubject(login)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
        .signWith(SignatureAlgorithm.HS256, jwtSecret)
        .compact();
  }

  public boolean validate(String token){
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
      return true;
    }
    catch (Exception e){
      System.out.println("invalid token");
    }
    return false;
  }

  public String getLoginFromToken(String token){
    return parseToken(token).getBody().getSubject();
  }

  public Jws<Claims> parseToken(String token){
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
  }
}
