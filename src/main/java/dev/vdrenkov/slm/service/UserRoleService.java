package dev.vdrenkov.slm.service;

import dev.vdrenkov.slm.dto.UserRoleDto;
import dev.vdrenkov.slm.entity.UserRole;
import dev.vdrenkov.slm.exception.UserRoleNotFoundException;
import dev.vdrenkov.slm.mapper.UserRoleMapper;
import dev.vdrenkov.slm.repository.UserRoleRepository;
import dev.vdrenkov.slm.request.UserRoleRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
/**
 * UserRoleService component.
 */
public class UserRoleService {

    private static final Logger log = LoggerFactory.getLogger(UserRoleService.class);

    private final UserRoleRepository userRepository;
    private final UserRoleMapper userMapper;

    @Autowired
    /**
     * Handles UserRoleService operation.
     * @param userRepository Repository dependency used by this component.
     * @param userMapper Mapper dependency used by this component.
     */
    public UserRoleService(final UserRoleRepository userRepository, final UserRoleMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Handles addUserRole operation.
     * @param userRoleRequest Request payload with input data.
     * @return Resulting userRole value.
     */
    public UserRole addUserRole(final UserRoleRequest userRoleRequest) {
        final UserRole userRole = new UserRole(userRoleRequest.getRole());

        log.info("Trying to add a new role");
        return userRepository.save(userRole);
    }

    /**
     * Handles getAllUserRoles operation.
     * @return List of userRoles.
     */
    public List<UserRole> getAllUserRoles() {
        log.info("Trying to retrieve all roles");
        return userRepository.findAll();
    }

    /**
     * Handles getAllUserRolesDto operation.
     * @return List of userRole DTOs.
     */
    public List<UserRoleDto> getAllUserRolesDto() {
        return userMapper.mapUserRolesToUserRolesDto(getAllUserRoles());
    }

    /**
     * Handles getUserRoleById operation.
     * @param id Identifier of the target entity.
     * @return Resulting userRole value.
     */
    public UserRole getUserRoleById(final int id) {
        log.info("Trying to retrieve role with an ID {}", id);
        return userRepository.findById(id).orElseThrow(UserRoleNotFoundException::new);
    }

    /**
     * Handles getUserRoleDtoById operation.
     * @param id Identifier of the target entity.
     * @return Resulting userRole DTO value.
     */
    public UserRoleDto getUserRoleDtoById(final int id) {
        return userMapper.mapUserRoleToUserRoleDto(getUserRoleById(id));
    }

    /**
     * Handles getUserRoleByRole operation.
     * @param role Role value.
     * @return Resulting userRole value.
     */
    public UserRole getUserRoleByRole(final String role) {
        return userRepository.findUserRoleByRole(role).orElseThrow(UserRoleNotFoundException::new);
    }
}
