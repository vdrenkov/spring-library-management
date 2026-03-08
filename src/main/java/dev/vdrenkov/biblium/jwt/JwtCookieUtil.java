package dev.vdrenkov.biblium.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static dev.vdrenkov.biblium.util.Constants.JWT_COOKIE_NAME;
import static dev.vdrenkov.biblium.util.Constants.JWT_TOKEN_VALIDITY;

/**
 * JwtCookieUtil component.
 */
@Component
public class JwtCookieUtil {
    private static final String COOKIE_PATH = "/";

    private final JwtTokenUtil tokenUtil;
    private final boolean secureCookie;
    private final String sameSitePolicy;

    /**
     * Handles JwtCookieUtil operation.
     *
     * @param tokenUtil
     *     JWT token utility dependency.
     * @param secureCookie
     *     Flag indicating whether the cookie is marked as secure.
     * @param sameSitePolicy
     *     SameSite policy value applied to the JWT cookie.
     */
    @Autowired
    public JwtCookieUtil(final JwtTokenUtil tokenUtil, @Value("${jwt.cookie.secure:false}") final boolean secureCookie,
        @Value("${jwt.cookie.same-site:Lax}") final String sameSitePolicy) {
        this.tokenUtil = tokenUtil;
        this.secureCookie = secureCookie;
        this.sameSitePolicy = sameSitePolicy;
    }

    /**
     * Handles createJWTCookie operation.
     *
     * @param userDetails
     *     Authenticated user details.
     * @return Resulting httpCookie value.
     */
    public HttpCookie createJWTCookie(final UserDetails userDetails) {
        final String jwt = tokenUtil.generateToken(userDetails);

        return ResponseCookie
            .from(JWT_COOKIE_NAME, jwt)
            .maxAge(JWT_TOKEN_VALIDITY)
            .httpOnly(true)
            .secure(secureCookie)
            .sameSite(sameSitePolicy)
            .path(COOKIE_PATH)
            .build();
    }
}
