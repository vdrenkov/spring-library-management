package bg.vdrenkov.configuration;

import bg.vdrenkov.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.http.HttpServletResponse;

import static bg.vdrenkov.util.Constants.JWT_COOKIE_NAME;

@EnableWebSecurity
public class WebSecurityConfiguration {

  private static final String ADMIN = "ADMIN";
  private static final String LIBRARIAN = "LIBRARIAN";

  private static final String[] AUTH_PATH = {
    "/login",
    "/register"
  };

  private static final String[] ADMIN_LIST = {
    "/admins/**",
    "/users",
    "/users/**",
    "/roles",
    "/roles/**",
    };

  private static final String[] LIBRARIAN_LIST = {
    "/authors",
    "/authors/**",
    "/books",
    "/books/**",
    "/clients",
    "/clients/**",
    "/orders",
    "/orders/**"
  };

  private final JwtRequestFilter jwtRequestFilter;

  @Autowired
  public WebSecurityConfiguration(JwtRequestFilter jwtRequestFilter) {
    this.jwtRequestFilter = jwtRequestFilter;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      .csrf()
      .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
      .and()
      .authorizeRequests()
      .antMatchers(AUTH_PATH).permitAll()
      .antMatchers(ADMIN_LIST).hasAuthority(ADMIN)
      .antMatchers(HttpMethod.POST, LIBRARIAN_LIST).hasAnyAuthority(ADMIN, LIBRARIAN)
      .antMatchers(HttpMethod.GET, LIBRARIAN_LIST).hasAnyAuthority(ADMIN, LIBRARIAN)
      .antMatchers(HttpMethod.PUT, LIBRARIAN_LIST).hasAnyAuthority(ADMIN, LIBRARIAN)
      .antMatchers(HttpMethod.DELETE, LIBRARIAN_LIST).hasAuthority(ADMIN)
      .anyRequest()
      .authenticated()
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
      .logout()
      .logoutUrl("/logout")
      .deleteCookies(JWT_COOKIE_NAME)
    .logoutSuccessHandler((request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK));
    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws
    Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}