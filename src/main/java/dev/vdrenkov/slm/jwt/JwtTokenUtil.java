package dev.vdrenkov.slm.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import static dev.vdrenkov.slm.util.Constants.JWT_TOKEN_VALIDITY;

@Component
/**
 * JwtTokenUtil component.
 */
public class JwtTokenUtil implements Serializable {

  @Value("${jwt.secret}")
  private String secret;

  /**
   * Handles getUsernameFromToken operation.
   * @param token JWT token value.
   * @return JWT token value.
   */
  public String getUsernameFromToken(final String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  /**
   * Handles getExpirationDateFromToken operation.
   * @param token JWT token value.
   * @return Resulting date value.
   */
  public Date getExpirationDateFromToken(final String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  /**
   * Handles getClaimFromToken operation.
   * @param token JWT token value.
   * @param claimsResolver Function used to resolve token claims.
   * @return Resulting <T> T value.
   */
  public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Handles getAllClaimsFromToken operation.
   * @param token JWT token value.
   * @return Resulting claims value.
   */
  private Claims getAllClaimsFromToken(final String token) {
    return Jwts.parser()
               .verifyWith(getSigningKey())
               .build()
               .parseSignedClaims(token)
               .getPayload();
  }

  /**
   * Handles getSigningKey operation.
   * @return Resulting secretKey value.
   */
  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Handles isTokenExpired operation.
   * @param token JWT token value.
   * @return Boolean flag indicating whether the condition is satisfied.
   */
  private Boolean isTokenExpired(final String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  /**
   * Handles generateToken operation.
   * @param userDetails Authenticated user details.
   * @return JWT token value.
   */
  public String generateToken(final UserDetails userDetails) {
    final Map<String, Object> claims = new HashMap<>();
    return doGenerateToken(claims, userDetails.getUsername());
  }

  /**
   * Handles doGenerateToken operation.
   * @param claims JWT claims data.
   * @param subject Token subject value.
   * @return JWT token value.
   */
  private String doGenerateToken(final Map<String, Object> claims, final String subject) {

    return Jwts.builder()
               .claims(claims)
               .subject(subject)
               .issuedAt(new Date(System.currentTimeMillis()))
               .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
               .signWith(getSigningKey(), Jwts.SIG.HS512)
               .compact();
  }

  /**
   * Handles validateToken operation.
   * @param token JWT token value.
   * @param userDetails Authenticated user details.
   * @return Boolean result of the operation.
   */
  public Boolean validateToken(final String token, final UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
