package dev.vdrenkov.controller;

import dev.vdrenkov.dto.UserRoleDto;
import dev.vdrenkov.entity.UserRole;
import dev.vdrenkov.request.UserRoleRequest;
import dev.vdrenkov.service.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class UserRoleController {

  private static final Logger log = LoggerFactory.getLogger(UserRoleController.class);

  private final UserRoleService userRoleService;

  @Autowired
  public UserRoleController(UserRoleService userRoleService) {
    this.userRoleService = userRoleService;
  }

  @PostMapping
  public ResponseEntity<Void> addUserRole(@RequestBody @Valid UserRoleRequest userRequest) {
    UserRole userRole = userRoleService.addUserRole(userRequest);

    URI location = UriComponentsBuilder.fromUriString("/roles/{id}")
                                       .buildAndExpand(userRole.getId())
                                       .toUri();
    log.info("A new role was added");
    return ResponseEntity.created(location).build();
  }

  @GetMapping
  public ResponseEntity<List<UserRoleDto>> getAllUserRoles() {
    log.info("All roles were requested from the database");
    return ResponseEntity.ok(userRoleService.getAllUserRolesDto());
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserRoleDto> getUserRoleById(@PathVariable int id) {
    log.info(String.format("Role with id %d was requested from the database", id));
    return ResponseEntity.ok(userRoleService.getUserRoleDtoById(id));
  }
}

