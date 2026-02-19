package bg.vdrenkov.service;

import bg.vdrenkov.dto.UserDto;
import bg.vdrenkov.entity.User;
import bg.vdrenkov.entity.UserRole;
import bg.vdrenkov.exception.UserNotFoundException;
import bg.vdrenkov.jwt.JwtCookieUtil;
import bg.vdrenkov.mapper.UserMapper;
import bg.vdrenkov.repository.UserRepository;
import bg.vdrenkov.request.AdminRequest;
import bg.vdrenkov.request.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

  private static final Logger log = LoggerFactory.getLogger(UserService.class);
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final JwtCookieUtil jwtCookieUtil;
  private final UserRepository userRepository;
  private final UserRoleService userRoleService;
  private final UserMapper userMapper;

  @Autowired
  public UserService(
    AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtCookieUtil jwtCookieUtil,
    UserRepository userRepository, UserRoleService userRoleService, UserMapper userMapper) {
    this.authenticationManager = authenticationManager;
    this.passwordEncoder = passwordEncoder;
    this.jwtCookieUtil = jwtCookieUtil;
    this.userRepository = userRepository;
    this.userRoleService = userRoleService;
    this.userMapper = userMapper;
  }

  public HttpCookie login(UserRequest userRequest) {
    UserDetails userDetails = (UserDetails) authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword())).getPrincipal();

    return jwtCookieUtil.createJWTCookie(userDetails);
  }

  public HttpCookie registerUser(UserRequest userRequest) {
    addUser(userRequest);

    return login(new UserRequest(userRequest.getUsername(), userRequest.getPassword()));
  }

  public User addUser(UserRequest userRequest) {
    String username = userRequest.getUsername();

    String password = passwordEncoder.encode(userRequest.getPassword());

    User user = new User(username, password, getLibrarianRole());

    log.info("Trying to add a new user");
    return userRepository.save(user);
  }

  public List<UserRole> getLibrarianRole() {
    String role = "LIBRARIAN";
    return Collections.singletonList(userRoleService.getUserRoleByRole(role));
  }

  public HttpCookie registerByAdmin(AdminRequest adminRequest) {
    addUserByAdmin(adminRequest);

    return login(new UserRequest(adminRequest.getUsername(), adminRequest.getPassword()));
  }

  public User addUserByAdmin(AdminRequest adminRequest) {
    String password = passwordEncoder.encode(adminRequest.getPassword());

    User user = new User(adminRequest.getUsername(), password, getUserRoles(adminRequest.getRolesIds()));

    log.info("Trying to add a new admin");
    return userRepository.save(user);
  }

  public List<UserRole> getUserRoles(List<Integer> rolesIds) {
    List<UserRole> roles = new ArrayList<>();

    for (int roleId : rolesIds) {
      roles.add(userRoleService.getUserRoleById(roleId));
    }

    return roles;
  }

  public List<User> getAllUsers() {
    log.info("Trying to retrieve all users");
    return userRepository.findAll();
  }

  public List<UserDto> getAllUsersDto() {
    return userMapper.mapUsersToUsersDto(getAllUsers());
  }

  public User getUserById(int id) {
    log.info(String.format("Trying to retrieve user with id %d", id));
    return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
  }

  public UserDto getUserDtoById(int id) {
    return userMapper.mapUserToUserDto(getUserById(id));
  }

  public User getUserByUsername(String username) {
    return userRepository.findUserByUsername(username);
  }
}

