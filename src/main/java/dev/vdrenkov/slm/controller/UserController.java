package dev.vdrenkov.slm.controller;

import dev.vdrenkov.slm.dto.UserDto;
import dev.vdrenkov.slm.entity.User;
import dev.vdrenkov.slm.request.AdminRequest;
import dev.vdrenkov.slm.request.UserRequest;
import dev.vdrenkov.slm.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * UserController component.
 */
@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private static final String USER_PATH = "/users";
    private static final String ADMIN_PATH = "/admins";

    private final UserService userService;

    /**
     * Handles UserController operation.
     *
     * @param userService
     *     Service dependency used by this component.
     */
    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles login operation.
     *
     * @param request
     *     Request payload with input data.
     * @return Response entity containing the operation result.
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid final UserRequest request) {
        final HttpCookie cookie = userService.login(request);
        log.info("A login request submitted");

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }

    /**
     * Handles registerUser operation.
     *
     * @param request
     *     Request payload with input data.
     * @return Response entity containing the operation result.
     */
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid final UserRequest request) {
        final HttpCookie cookie = userService.registerUser(request);
        log.info("A register request submitted");

        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }

    /**
     * Handles registerAdmin operation.
     *
     * @param request
     *     Request payload with input data.
     * @return Response entity containing the operation result.
     */
    @PostMapping(ADMIN_PATH + "/register")
    public ResponseEntity<Void> registerAdmin(@RequestBody @Valid final AdminRequest request) {
        final User user = userService.registerByAdmin(request);
        final URI location = UriComponentsBuilder
            .fromUriString(USER_PATH + "/{id}")
            .buildAndExpand(user.getId())
            .toUri();
        log.info("An admin register request submitted");

        return ResponseEntity.created(location).build();
    }

    /**
     * Handles getAllUsers operation.
     *
     * @return Response entity containing the requested data.
     */
    @GetMapping(USER_PATH)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.info("All users requested from the database");
        return ResponseEntity.ok(userService.getAllUsersDto());
    }

    /**
     * Handles getUserById operation.
     *
     * @param id
     *     Identifier of the target entity.
     * @return Response entity containing the requested data.
     */
    @GetMapping(USER_PATH + "/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable final int id) {
        log.info("User with an ID {} requested from the database.", id);
        return ResponseEntity.ok(userService.getUserDtoById(id));
    }
}
