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
public class UserRoleService {

    private static final Logger log = LoggerFactory.getLogger(UserRoleService.class);

    private final UserRoleRepository userRepository;
    private final UserRoleMapper userMapper;

    @Autowired
    public UserRoleService(UserRoleRepository userRepository, UserRoleMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserRole addUserRole(UserRoleRequest userRoleRequest) {
        UserRole userRole = new UserRole(userRoleRequest.getRole());

        log.info("Trying to add a new role");
        return userRepository.save(userRole);
    }

    public List<UserRole> getAllUserRoles() {
        log.info("Trying to retrieve all roles");
        return userRepository.findAll();
    }

    public List<UserRoleDto> getAllUserRolesDto() {
        return userMapper.mapUserRolesToUserRolesDto(getAllUserRoles());
    }

    public UserRole getUserRoleById(int id) {
        log.info("Trying to retrieve role with an ID {}", id);
        return userRepository.findById(id).orElseThrow(UserRoleNotFoundException::new);
    }

    public UserRoleDto getUserRoleDtoById(int id) {
        return userMapper.mapUserRoleToUserRoleDto(getUserRoleById(id));
    }

    public UserRole getUserRoleByRole(String role) {
        return userRepository.findUserRoleByRole(role);
    }
}

