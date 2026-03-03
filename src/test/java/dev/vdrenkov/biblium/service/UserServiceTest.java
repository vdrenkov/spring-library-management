package dev.vdrenkov.biblium.service;

import dev.vdrenkov.biblium.entity.User;
import dev.vdrenkov.biblium.entity.UserRole;
import dev.vdrenkov.biblium.jwt.JwtCookieUtil;
import dev.vdrenkov.biblium.repository.UserRepository;
import dev.vdrenkov.biblium.request.AdminRequest;
import dev.vdrenkov.biblium.request.UserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtCookieUtil jwtCookieUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleService userRoleService;

    @Test
    void testAddUser_success() {
        final UserRole librarianRole = new UserRole(1, "LIBRARIAN");
        when(userRepository.existsByUsername("librarian")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded-password");
        when(userRoleService.getUserRoleByRole("LIBRARIAN")).thenReturn(librarianRole);

        userService.addUser(new UserRequest("librarian", "password123"));

        final ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        final User savedUser = userCaptor.getValue();

        assertEquals("librarian", savedUser.getUsername());
        assertEquals("encoded-password", savedUser.getPassword());
        assertEquals(1, savedUser.getUserRoles().size());
        assertEquals("LIBRARIAN", savedUser.getUserRoles().getFirst().getRole());
    }

    @Test
    void testAddUser_duplicateUsername_throwsException() {
        when(userRepository.existsByUsername("librarian")).thenReturn(true);

        final UserRequest userRequest = new UserRequest("librarian", "password123");
        assertThrows(IllegalArgumentException.class, () -> userService.addUser(userRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testAddUserByAdmin_success() {
        final UserRole adminRole = new UserRole(1, "ADMIN");
        final UserRole librarianRole = new UserRole(2, "LIBRARIAN");
        when(userRepository.existsByUsername("admin2")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded-password");
        when(userRoleService.getUserRoleById(1)).thenReturn(adminRole);
        when(userRoleService.getUserRoleById(2)).thenReturn(librarianRole);

        userService.addUserByAdmin(new AdminRequest("admin2", "password123", List.of(1, 2)));

        final ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        final User savedUser = userCaptor.getValue();

        assertEquals("admin2", savedUser.getUsername());
        assertEquals("encoded-password", savedUser.getPassword());
        assertEquals(2, savedUser.getUserRoles().size());
    }

    @Test
    void testRegisterByAdmin_createsUserWithoutAuthenticationSideEffects() {
        final UserRole adminRole = new UserRole(1, "ADMIN");
        final AdminRequest request = new AdminRequest("admin2", "password123", List.of(1));
        final User savedUser = new User(10, "admin2", "encoded-password", List.of(adminRole));

        when(userRepository.existsByUsername("admin2")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded-password");
        when(userRoleService.getUserRoleById(1)).thenReturn(adminRole);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        final User result = userService.registerByAdmin(request);

        assertEquals(10, result.getId());
        verifyNoInteractions(authenticationManager, jwtCookieUtil);
    }
}
