package dev.vdrenkov.slm.service;

import dev.vdrenkov.slm.dto.UserDto;
import dev.vdrenkov.slm.entity.User;
import dev.vdrenkov.slm.entity.UserRole;
import dev.vdrenkov.slm.exception.UserNotFoundException;
import dev.vdrenkov.slm.jwt.JwtCookieUtil;
import dev.vdrenkov.slm.mapper.UserMapper;
import dev.vdrenkov.slm.repository.UserRepository;
import dev.vdrenkov.slm.request.AdminRequest;
import dev.vdrenkov.slm.request.UserRequest;
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
    private static final String LIBRARIAN_ROLE = "LIBRARIAN";
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtCookieUtil jwtCookieUtil;
    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final UserMapper userMapper;

    @Autowired
    public UserService(final AuthenticationManager authenticationManager, final PasswordEncoder passwordEncoder,
        final JwtCookieUtil jwtCookieUtil, final UserRepository userRepository, final UserRoleService userRoleService,
        final UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtCookieUtil = jwtCookieUtil;
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.userMapper = userMapper;
    }

    public HttpCookie login(final UserRequest userRequest) {
        final UserDetails userDetails = (UserDetails) authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()))
            .getPrincipal();

        return jwtCookieUtil.createJWTCookie(userDetails);
    }

    public HttpCookie registerUser(final UserRequest userRequest) {
        addUser(userRequest);

        return login(new UserRequest(userRequest.getUsername(), userRequest.getPassword()));
    }

    public void addUser(final UserRequest userRequest) {
        final String username = userRequest.getUsername();
        validateUsernameIsAvailable(username);

        final String password = passwordEncoder.encode(userRequest.getPassword());

        final User user = new User(username, password, getLibrarianRole());

        log.info("Trying to add a new user");
        userRepository.save(user);
    }

    public List<UserRole> getLibrarianRole() {
        return Collections.singletonList(userRoleService.getUserRoleByRole(LIBRARIAN_ROLE));
    }

    public HttpCookie registerByAdmin(final AdminRequest adminRequest) {
        addUserByAdmin(adminRequest);

        return login(new UserRequest(adminRequest.getUsername(), adminRequest.getPassword()));
    }

    public void addUserByAdmin(final AdminRequest adminRequest) {
        validateUsernameIsAvailable(adminRequest.getUsername());

        final String password = passwordEncoder.encode(adminRequest.getPassword());

        final User user = new User(adminRequest.getUsername(), password, getUserRoles(adminRequest.getRolesIds()));

        log.info("Trying to add a new admin");
        userRepository.save(user);
    }

    private void validateUsernameIsAvailable(final String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("A user with this username already exists");
        }
    }

    public List<UserRole> getUserRoles(final List<Integer> rolesIds) {
        final List<UserRole> roles = new ArrayList<>();

        for (final int roleId : rolesIds) {
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

    public User getUserById(final int id) {
        log.info("Trying to retrieve user with an ID {}", id);
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public UserDto getUserDtoById(final int id) {
        return userMapper.mapUserToUserDto(getUserById(id));
    }
}

