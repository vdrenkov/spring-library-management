package dev.vdrenkov.slm.mapper;

import dev.vdrenkov.slm.dto.UserDto;
import dev.vdrenkov.slm.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
/**
 * UserMapper component.
 */
public class UserMapper {

  private static final Logger log = LoggerFactory.getLogger(UserMapper.class);

  private final UserRoleMapper userRoleMapper;

  @Autowired
  /**
   * Handles UserMapper operation.
   * @param userRoleMapper Mapper dependency used by this component.
   */
  public UserMapper(final UserRoleMapper userRoleMapper) {
    this.userRoleMapper = userRoleMapper;
  }

  /**
   * Handles mapUsersToUsersDto operation.
   * @param users User entities to map.
   * @return List of user DTOs.
   */
  public List<UserDto> mapUsersToUsersDto(final List<User> users) {
    final List<UserDto> usersDto = new ArrayList<>();

    for (final User user : users) {
      usersDto.add(mapUserToUserDto(user));
    }

    usersDto.sort(Comparator.comparing(UserDto::getUsername));
    log.debug("Users' list mapped to users' DTOs list");
    return usersDto;
  }

  /**
   * Handles mapUserToUserDto operation.
   * @param user User entity value.
   * @return Resulting user DTO value.
   */
  public UserDto mapUserToUserDto(final User user) {
    log.debug("User mapped to user DTO");
    return new UserDto(user.getUsername(), userRoleMapper.mapUserRolesToUserRolesDto(user.getUserRoles()));
  }
}
