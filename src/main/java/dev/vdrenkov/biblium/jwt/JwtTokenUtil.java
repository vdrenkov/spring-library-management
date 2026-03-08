package dev.vdrenkov.biblium.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static dev.vdrenkov.biblium.jwt.JwtConstants.JWT_TOKEN_VALIDITY;

/**
 * JwtTokenUtil component.
 */
@Component
public class JwtTokenUtil implements Serializable {

    @Serial
    private static final long serialVersionUID = -2482501454063314717L;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Handles getUsernameFromToken operation.
     *
     * @param token
     *     JWT token value.
     * @return JWT token value.
     */
    public String getUsernameFromToken(final String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Handles getExpirationInstantFromToken operation.
     *
     * @param token
     *     JWT token value.
     * @return Token expiration instant.
     */
    public Instant getExpirationInstantFromToken(final String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.getExpiration().toInstant();
    }

    /**
     * Handles getClaimFromToken operation.
     *
     * @param <T>
     *     The desired claim type. Will not be null.
     * @param token
     *     JWT token value.
     * @param claimsResolver
     *     Function used to resolve token claims.
     * @return Resulting T value.
     */
    public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Handles getAllClaimsFromToken operation.
     *
     * @param token
     *     JWT token value.
     * @return Resulting claims value.
     */
    private Claims getAllClaimsFromToken(final String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    /**
     * Handles getSigningKey operation.
     *
     * @return Resulting secretKey value.
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Handles isTokenExpired operation.
     *
     * @param token
     *     JWT token value.
     * @return Boolean flag indicating whether the condition is satisfied.
     */
    private Boolean isTokenExpired(final String token) {
        final Instant expiration = getExpirationInstantFromToken(token);
        return expiration.isBefore(Instant.now());
    }

    /**
     * Handles generateToken operation.
     *
     * @param userDetails
     *     Authenticated user details.
     * @return JWT token value.
     */
    public String generateToken(final UserDetails userDetails) {
        final Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * Handles doGenerateToken operation.
     *
     * @param claims
     *     JWT claims data.
     * @param subject
     *     Token subject value.
     * @return JWT token value.
     */
    private String doGenerateToken(final Map<String, Object> claims, final String subject) {
        final Instant issuedAt = Instant.now();
        final Instant expiresAt = issuedAt.plusSeconds(JWT_TOKEN_VALIDITY);

        return Jwts
            .builder()
            .claims(claims)
            .subject(subject)
            .issuedAt(java.util.Date.from(issuedAt))
            .expiration(java.util.Date.from(expiresAt))
            .signWith(getSigningKey(), Jwts.SIG.HS512)
            .compact();
    }

    /**
     * Handles validateToken operation.
     *
     * @param token
     *     JWT token value.
     * @param userDetails
     *     Authenticated user details.
     * @return Boolean result of the operation.
     */
    public Boolean validateToken(final String token, final UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

