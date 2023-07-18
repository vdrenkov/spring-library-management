package bg.vdrenkov.mapper;

import bg.vdrenkov.dto.UserRoleDto;
import bg.vdrenkov.entity.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class UserRoleMapper {

  private static final Logger log = LoggerFactory.getLogger(UserRoleMapper.class);

  public List<UserRoleDto> mapUserRolesToUserRolesDto(List<UserRole> userRoles) {
    List<UserRoleDto> userRolesDto = new ArrayList<>();

    for (UserRole userRole : userRoles) {
      userRolesDto.add(mapUserRoleToUserRoleDto(userRole));
    }

    userRolesDto.sort(Comparator.comparing(UserRoleDto::getRole));
    log.info("Users' list mapper to users' DTOs list");
    return userRolesDto;
  }

  public UserRoleDto mapUserRoleToUserRoleDto(UserRole userRole) {
    log.info("User role mapped to user role DTO");
    return new UserRoleDto(userRole.getRole());
  }
}