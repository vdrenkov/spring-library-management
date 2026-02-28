package dev.vdrenkov.slm.configuration;

import dev.vdrenkov.slm.entity.User;
import dev.vdrenkov.slm.entity.UserRole;
import dev.vdrenkov.slm.repository.UserRepository;
import dev.vdrenkov.slm.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleBootstrapRunnerTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void testRun_bootstrapsMissingRoles() {
        when(userRoleRepository.existsByRole("ADMIN")).thenReturn(false);
        when(userRoleRepository.existsByRole("LIBRARIAN")).thenReturn(false);

        RoleBootstrapRunner runner = new RoleBootstrapRunner(
            userRoleRepository,
            userRepository,
            passwordEncoder,
            true,
            "",
            ""
        );

        runner.run();

        verify(userRoleRepository).save(new UserRole("ADMIN"));
        verify(userRoleRepository).save(new UserRole("LIBRARIAN"));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRun_bootstrapsAdminWhenConfigured() {
        UserRole adminRole = new UserRole(1, "ADMIN");
        when(userRoleRepository.existsByRole("ADMIN")).thenReturn(true);
        when(userRoleRepository.existsByRole("LIBRARIAN")).thenReturn(true);
        when(userRoleRepository.findUserRoleByRole("ADMIN")).thenReturn(Optional.of(adminRole));
        when(userRepository.existsByUsername("admin")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded-password");

        RoleBootstrapRunner runner = new RoleBootstrapRunner(
            userRoleRepository,
            userRepository,
            passwordEncoder,
            true,
            "admin",
            "password123"
        );

        runner.run();

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals("admin", savedUser.getUsername());
        assertEquals("encoded-password", savedUser.getPassword());
        assertTrue(savedUser.getUserRoles().stream().anyMatch(role -> "ADMIN".equals(role.getRole())));
    }
}
