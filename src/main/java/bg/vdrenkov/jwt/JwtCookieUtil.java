package bg.vdrenkov.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static bg.vdrenkov.util.Constants.JWT_COOKIE_NAME;
import static bg.vdrenkov.util.Constants.JWT_TOKEN_VALIDITY;

@Component
public class JwtCookieUtil {

  private final JwtTokenUtil tokenUtil;

  @Autowired
  public JwtCookieUtil(JwtTokenUtil tokenUtil) {
    this.tokenUtil = tokenUtil;
  }

  public HttpCookie createJWTCookie(UserDetails userDetails) {
    String jwt = tokenUtil.generateToken(userDetails);

    return ResponseCookie.from(JWT_COOKIE_NAME, jwt)
                         .maxAge(JWT_TOKEN_VALIDITY)
                         .httpOnly(true)
                         .build();
  }
}