package dev.vdrenkov.slm.jwt;

import dev.vdrenkov.slm.entity.User;
import dev.vdrenkov.slm.entity.UserRole;
import dev.vdrenkov.slm.exception.UserNotFoundException;
import dev.vdrenkov.slm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public JwtUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findUserByUsername(username);

    if (Objects.isNull(user)) {
      throw new UserNotFoundException();
    }

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    for (UserRole role : user.getUserRoles()) {
      authorities.add(new SimpleGrantedAuthority(role.getRole()));
    }

    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
  }
}
