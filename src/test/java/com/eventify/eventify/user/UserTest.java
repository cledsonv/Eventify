package com.eventify.eventify.user;


import com.eventify.eventify.Features.Event.Entities.Event;
import com.eventify.eventify.Features.Registration.Entities.Registration;
import com.eventify.eventify.Features.User.Entities.User;
import com.eventify.eventify.Features.User.Enum.Role;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUser() {
        User user = new User("Valid Name", "valid@email.com", "password123", Role.PARTICIPANTE);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidEmail() {
        User user = new User("Valid Name", "invalid-email", "password123", Role.PARTICIPANTE);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());

        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Email deve ser válido", violation.getMessage());
    }

    @Test
    void testBlankName() {
        User user = new User("", "valid@email.com", "password123", Role.PARTICIPANTE);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());

        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Nome é obrigatório", violation.getMessage());
    }

    @Test
    void testBlankPassword() {
        User user = new User("Valid Name", "valid@email.com", "", Role.PARTICIPANTE);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Esperamos 2 violações: uma para @NotBlank e outra para @Size
        assertEquals(2, violations.size());

        // Verificar se as mensagens correspondem às violações esperadas
        for (ConstraintViolation<User> violation : violations) {
            String message = violation.getMessage();
            assertTrue(
                    message.equals("Senha é obrigatória") || message.equals("A senha deve ter no mínimo 6 caracteres")
            );
        }
    }

    @Test
    void testPasswordTooShort() {
        User user = new User("Valid Name", "valid@email.com", "123", Role.PARTICIPANTE);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());

        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("A senha deve ter no mínimo 6 caracteres", violation.getMessage());
    }

    @Test
    void testGrantedAuthorities() {
        User user = new User("Valid Name", "valid@email.com", "password123", Role.ADMIN);

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertEquals(1, authorities.size());
        assertEquals("ROLE_ADMIN", authorities.iterator().next().getAuthority());
    }

    @Test
    void testUserDetailsMethods() {
        User user = new User("Valid Name", "valid@email.com", "password123", Role.PARTICIPANTE);

        assertEquals(user.getEmail(), user.getUsername());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }

    @Test
    void testUserEventRelationship() {
        User user = new User("Valid Name", "valid@email.com", "password123", Role.ORGANIZADOR);
        Event event = new Event();
        event.setOrganizer(user);

        user.setEventsOrganized(List.of(event));

        assertEquals(1, user.getEventsOrganized().size());
        assertEquals(user, user.getEventsOrganized().get(0).getOrganizer());
    }
}
