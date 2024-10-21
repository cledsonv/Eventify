package com.eventify.eventify.user;

import com.eventify.eventify.Features.User.Dtos.UserDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenNameIsBlank_thenValidationFails() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("");  // Nome vazio
        userDTO.setEmail("valid@email.com");
        userDTO.setPassword("password123");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        assertEquals(1, violations.size());
        assertEquals("Nome é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    public void whenEmailIsInvalid_thenValidationFails() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Valid Name");
        userDTO.setEmail("invalid-email");  // Email inválido
        userDTO.setPassword("password123");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        assertEquals(1, violations.size());
        assertEquals("Email deve ser válido", violations.iterator().next().getMessage());
    }

    @Test
    public void whenEmailIsBlank_thenValidationFails() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Valid Name");
        userDTO.setEmail("");  // Email vazio
        userDTO.setPassword("password123");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        assertEquals(1, violations.size());
        assertEquals("Email é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    public void whenPasswordIsTooShort_thenValidationFails() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Valid Name");
        userDTO.setEmail("valid@email.com");
        userDTO.setPassword("123");  // Senha muito curta

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        assertEquals(1, violations.size());
        assertEquals("A senha deve ter no mínimo 6 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    public void whenPasswordIsBlank_thenValidationFails() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Valid Name");
        userDTO.setEmail("valid@email.com");
        userDTO.setPassword("");  // Senha em branco

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

        // Esperando 2 violações: uma para @NotBlank e outra para @Size
        assertEquals(2, violations.size());

        // Verificar se as mensagens de erro correspondem às violações esperadas
        for (ConstraintViolation<UserDTO> violation : violations) {
            String message = violation.getMessage();
            assertTrue(message.equals("Senha é obrigatória") || message.equals("A senha deve ter no mínimo 6 caracteres"));
        }
    }

    @Test
    public void whenAllFieldsAreValid_thenNoValidationErrors() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Valid Name");
        userDTO.setEmail("valid@email.com");
        userDTO.setPassword("password123");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        assertTrue(violations.isEmpty());
    }
}
