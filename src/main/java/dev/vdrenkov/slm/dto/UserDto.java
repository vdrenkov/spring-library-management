package dev.vdrenkov.slm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * UserDto component.
 */
@AllArgsConstructor
@Data
public class UserDto {
    private String username;
    private List<UserRoleDto> userRoleDtos;
}
