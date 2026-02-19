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
public class UserMapper {

  private static final Logger log = LoggerFactory.getLogger(UserMapper.class);

  private final UserRoleMapper userRoleMapper;

  @Autowired
  public UserMapper(UserRoleMapper userRoleMapper) {
    this.userRoleMapper = userRoleMapper;
  }

  public List<UserDto> mapUsersToUsersDto(List<User> users) {
    List<UserDto> usersDto = new ArrayList<>();

    for (User user : users) {
      usersDto.add(mapUserToUserDto(user));
    }

    usersDto.sort(Comparator.comparing(UserDto::getUsername));
    log.info("Users' list mapped to users' DTOs list");
    return usersDto;
  }

  public UserDto mapUserToUserDto(User user) {
    log.info("User mapped to user DTO");
    return new UserDto(user.getUsername(), userRoleMapper.mapUserRolesToUserRolesDto(user.getUserRoles()));
  }
}
