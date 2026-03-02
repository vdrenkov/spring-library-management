package dev.vdrenkov.slm.mapper;

import dev.vdrenkov.slm.dto.UserDto;
import dev.vdrenkov.slm.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * UserMapper component.
 */
public final class UserMapper {
    private static final Logger log = LoggerFactory.getLogger(UserMapper.class);

    private UserMapper() {
        /* This utility class should not be instantiated */
    }

    /**
     * Handles mapUsersToUsersDto operation.
     *
     * @param users
     *     User entities to map.
     * @return List of user DTOs.
     */
    public static List<UserDto> mapUsersToUsersDto(final List<User> users) {
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
     *
     * @param user
     *     User entity value.
     * @return Resulting user DTO value.
     */
    public static UserDto mapUserToUserDto(final User user) {
        log.debug("User mapped to user DTO");
        return new UserDto(user.getUsername(), UserRoleMapper.mapUserRolesToUserRolesDto(user.getUserRoles()));
    }
}
