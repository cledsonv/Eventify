package com.eventify.eventify.registration;

import com.eventify.eventify.Features.Registration.DTO.RegistrationDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegistrationDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenNoValidationErrors() {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setUserId(1L);
        registrationDTO.setEventId(1L);
        registrationDTO.setCreatedAt(new Date());

        Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(registrationDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenUserIdIsNull_thenValidationFails() {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setEventId(1L);
        registrationDTO.setCreatedAt(new Date());

        Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(registrationDTO);
        assertEquals(1, violations.size());

        ConstraintViolation<RegistrationDTO> violation = violations.iterator().next();
        assertEquals("User ID cannot be null", violation.getMessage());
    }

    @Test
    void whenEventIdIsNull_thenValidationFails() {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setUserId(1L);
        registrationDTO.setCreatedAt(new Date());

        Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(registrationDTO);
        assertEquals(1, violations.size());

        ConstraintViolation<RegistrationDTO> violation = violations.iterator().next();
        assertEquals("Event ID cannot be null", violation.getMessage());
    }

    @Test
    void whenCreatedAtIsNull_thenValidationFails() {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setUserId(1L);
        registrationDTO.setEventId(1L);

        Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(registrationDTO);
        assertEquals(1, violations.size());

        ConstraintViolation<RegistrationDTO> violation = violations.iterator().next();
        assertEquals("Registration date cannot be null", violation.getMessage());
    }
}
