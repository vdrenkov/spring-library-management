package dev.vdrenkov.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static dev.vdrenkov.util.Constants.JWT_COOKIE_NAME;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  private final Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);

  private final JwtTokenUtil tokenUtil;
  private final JwtUserDetailsService userDetailsService;

  @Autowired
  public JwtRequestFilter(JwtTokenUtil tokenUtil, JwtUserDetailsService userDetailsService) {
    this.tokenUtil = tokenUtil;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(
    HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
    ServletException, IOException {
    String token = getJwtToken(request.getCookies());
    String username = null;

    try {
      if (token != null && !token.isEmpty()) {
        log.info("JWT found");
        username = tokenUtil.getUsernameFromToken(token);
      }

      if (SecurityContextHolder.getContext().getAuthentication() == null && username != null) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (tokenUtil.validateToken(token, userDetails)) {
          UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }
    } catch (Exception exception) {
      log.warn("Failed to authenticate request from JWT cookie", exception);
    }

    chain.doFilter(request, response);
  }

  private String getJwtToken(Cookie[] cookies) {
    if (cookies != null) {
      return Arrays.stream(cookies)
                   .filter(cookie -> JWT_COOKIE_NAME.equals(cookie.getName()))
                   .findFirst()
                   .map(Cookie::getValue)
                   .orElse(null);
    }
    return null;
  }
}

