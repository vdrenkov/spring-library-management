package dev.vdrenkov.slm.jwt;

import dev.vdrenkov.slm.entity.User;
import dev.vdrenkov.slm.entity.UserRole;
import dev.vdrenkov.slm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
/**
 * JwtUserDetailsService component.
 */
public class JwtUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  /**
   * Handles JwtUserDetailsService operation.
   * @param userRepository Repository dependency used by this component.
   */
  public JwtUserDetailsService(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  /**
   * Handles loadUserByUsername operation.
   * @param username Username value.
   * @return Loaded user details instance.
   */
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    final User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("No such user found in the database"));

    final List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    for (final UserRole role : user.getUserRoles()) {
      authorities.add(new SimpleGrantedAuthority(role.getRole()));
    }

    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
  }
}
