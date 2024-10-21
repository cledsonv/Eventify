package com.eventify.eventify.user;

import com.eventify.eventify.Core.Exception.RoleNotPermisionException;
import com.eventify.eventify.Core.Security.JwtUtil;
import com.eventify.eventify.Features.User.Entities.User;
import com.eventify.eventify.Features.User.Enum.Role;
import com.eventify.eventify.Features.User.Repositories.UserRepository;
import com.eventify.eventify.Features.User.Service.UserService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        User user = new User();
        user.setPassword("rawpassword");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedpassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals("encodedpassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUserRoleByNonAdmin_shouldThrowException() {
        User user = new User();
        user.setRole(Role.PARTICIPANTE);

        assertThrows(RoleNotPermisionException.class, () -> {
            userService.updateUserRole("email@example.com", Role.ORGANIZADOR, user);
        });
    }

    @Test
    void testUpdateUserRoleByAdmin() {
        User adminUser = new User();
        adminUser.setRole(Role.ADMIN);

        User targetUser = new User();
        targetUser.setEmail("email@example.com");

        when(userRepository.findByEmail("email@example.com")).thenReturn(Optional.of(targetUser));
        when(userRepository.save(any(User.class))).thenReturn(targetUser);

        User updatedUser = userService.updateUserRole("email@example.com", Role.ORGANIZADOR, adminUser);

        assertEquals(Role.ORGANIZADOR, updatedUser.getRole());
        verify(userRepository, times(1)).findByEmail("email@example.com");
        verify(userRepository, times(1)).save(targetUser);
    }

    @Test
    void testGetUsersByNonAdmin_shouldThrowException() {
        User user = new User();
        user.setRole(Role.PARTICIPANTE);

        assertThrows(RoleNotPermisionException.class, () -> {
            userService.getUsers(user);
        });
    }

    @Test
    void testGetUsersByAdmin() {
        User adminUser = new User();
        adminUser.setRole(Role.ADMIN);

        when(userRepository.findAll()).thenReturn(List.of(new User()));

        List<User> users = userService.getUsers(adminUser);

        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testSignInWithValidCredentials() {
        User user = new User();
        user.setPassword("encodedpassword");

        when(userRepository.findByEmail("valid@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtUtil.generateToken(any(User.class))).thenReturn("jwt-token");

        String token = userService.singIn("valid@example.com", "password");

        assertEquals("jwt-token", token);
        verify(jwtUtil, times(1)).generateToken(user);
    }

    @Test
    void testSignInWithInvalidCredentials_shouldThrowException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ConstraintViolationException.class, () -> {
            userService.singIn("invalid@example.com", "password");
        });
    }

    @Test
    void testDeleteUserByAdmin() {
        User adminUser = new User();
        adminUser.setRole(Role.ADMIN);

        User targetUser = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(targetUser));

        userService.deleteUser(1L, adminUser);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUserByNonAdmin_shouldThrowException() {
        User user = new User();
        user.setRole(Role.PARTICIPANTE);

        assertThrows(RoleNotPermisionException.class, () -> {
            userService.deleteUser(1L, user);
        });
    }
}
