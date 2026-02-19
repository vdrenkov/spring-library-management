package bg.vdrenkov.controller;

import bg.vdrenkov.dto.UserDto;
import bg.vdrenkov.request.AdminRequest;
import bg.vdrenkov.request.UserRequest;
import bg.vdrenkov.service.UserService;
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

import java.util.List;

@RestController
public class UserController {

  private static final String USER_PATH = "/users";
  private static final String ADMIN_PATH = "/admins";

  private static final Logger log = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<Void> login(@RequestBody UserRequest request) {
    HttpCookie cookie = userService.login(request);
    log.info("A login request has been submitted");

    return ResponseEntity.status(HttpStatus.CREATED)
                         .header(HttpHeaders.SET_COOKIE, cookie.toString())
                         .build();
  }

  @PostMapping("/register")
  public ResponseEntity<Void> registerUser(@RequestBody UserRequest request) {
    HttpCookie cookie = userService.registerUser(request);
    log.info("A register request has been submitted");

    return ResponseEntity.status(HttpStatus.CREATED)
                         .header(HttpHeaders.SET_COOKIE, cookie.toString())
                         .build();
  }

  @PostMapping(ADMIN_PATH + "/register")
  public ResponseEntity<Void> registerAdmin(@RequestBody AdminRequest request) {
    HttpCookie cookie = userService.registerByAdmin(request);
    log.info("A register request has been submitted");

    return ResponseEntity.status(HttpStatus.CREATED)
                         .header(HttpHeaders.SET_COOKIE, cookie.toString())
                         .build();
  }

  @GetMapping(USER_PATH)
  public ResponseEntity<List<UserDto>> getAllUsers() {
    log.info("All users were requested from the database");
    return ResponseEntity.ok(userService.getAllUsersDto());
  }

  @GetMapping(USER_PATH + "/{id}")
  public ResponseEntity<UserDto> getUserById(@PathVariable int id) {
    log.info(String.format("User with id %d was requested from the database", id));
    return ResponseEntity.ok(userService.getUserDtoById(id));
  }
}
