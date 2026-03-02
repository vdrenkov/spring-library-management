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
/**
 * UserService component.
 */
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
    /**
     * Handles UserService operation.
     * @param authenticationManager Authentication manager dependency.
     * @param passwordEncoder Password encoder dependency.
     * @param jwtCookieUtil JWT cookie utility dependency.
     * @param userRepository Repository dependency used by this component.
     * @param userRoleService Service dependency used by this component.
     * @param userMapper Mapper dependency used by this component.
     */
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

    /**
     * Handles login operation.
     * @param userRequest Request payload with input data.
     * @return Resulting httpCookie value.
     */
    public HttpCookie login(final UserRequest userRequest) {
        final UserDetails userDetails = (UserDetails) authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()))
            .getPrincipal();

        return jwtCookieUtil.createJWTCookie(userDetails);
    }

    /**
     * Handles registerUser operation.
     * @param userRequest Request payload with input data.
     * @return Resulting httpCookie value.
     */
    public HttpCookie registerUser(final UserRequest userRequest) {
        addUser(userRequest);

        return login(new UserRequest(userRequest.getUsername(), userRequest.getPassword()));
    }

    /**
     * Handles addUser operation.
     * @param userRequest Request payload with input data.
     */
    public void addUser(final UserRequest userRequest) {
        final String username = userRequest.getUsername();
        validateUsernameIsAvailable(username);

        final String password = passwordEncoder.encode(userRequest.getPassword());

        final User user = new User(username, password, getLibrarianRole());

        log.info("Trying to add a new user");
        userRepository.save(user);
    }

    /**
     * Handles getLibrarianRole operation.
     * @return List of userRoles.
     */
    public List<UserRole> getLibrarianRole() {
        return Collections.singletonList(userRoleService.getUserRoleByRole(LIBRARIAN_ROLE));
    }

    /**
     * Handles registerByAdmin operation.
     * @param adminRequest Request payload with input data.
     * @return Resulting httpCookie value.
     */
    public HttpCookie registerByAdmin(final AdminRequest adminRequest) {
        addUserByAdmin(adminRequest);

        return login(new UserRequest(adminRequest.getUsername(), adminRequest.getPassword()));
    }

    /**
     * Handles addUserByAdmin operation.
     * @param adminRequest Request payload with input data.
     */
    public void addUserByAdmin(final AdminRequest adminRequest) {
        validateUsernameIsAvailable(adminRequest.getUsername());

        final String password = passwordEncoder.encode(adminRequest.getPassword());

        final User user = new User(adminRequest.getUsername(), password, getUserRoles(adminRequest.getRolesIds()));

        log.info("Trying to add a new admin");
        userRepository.save(user);
    }

    /**
     * Handles validateUsernameIsAvailable operation.
     * @param username Username value.
     */
    private void validateUsernameIsAvailable(final String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("A user with this username already exists");
        }
    }

    /**
     * Handles getUserRoles operation.
     * @param rolesIds Collection of target entity identifiers.
     * @return List of userRoles.
     */
    public List<UserRole> getUserRoles(final List<Integer> rolesIds) {
        final List<UserRole> roles = new ArrayList<>();

        for (final int roleId : rolesIds) {
            roles.add(userRoleService.getUserRoleById(roleId));
        }

        return roles;
    }

    /**
     * Handles getAllUsers operation.
     * @return List of users.
     */
    public List<User> getAllUsers() {
        log.info("Trying to retrieve all users");
        return userRepository.findAll();
    }

    /**
     * Handles getAllUsersDto operation.
     * @return List of user DTOs.
     */
    public List<UserDto> getAllUsersDto() {
        return userMapper.mapUsersToUsersDto(getAllUsers());
    }

    /**
     * Handles getUserById operation.
     * @param id Identifier of the target entity.
     * @return Resulting user value.
     */
    public User getUserById(final int id) {
        log.info("Trying to retrieve user with an ID {}", id);
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    /**
     * Handles getUserDtoById operation.
     * @param id Identifier of the target entity.
     * @return Resulting user DTO value.
     */
    public UserDto getUserDtoById(final int id) {
        return UserMapper.mapUserToUserDto(getUserById(id));
    }
}
