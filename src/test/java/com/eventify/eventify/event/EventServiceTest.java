package com.eventify.eventify.event;

import com.eventify.eventify.Features.Event.DTO.EventDTO;
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

public class EventServiceTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenNameIsNull_thenValidationFails() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName(null);  // O nome não pode ser nulo
        eventDTO.setDate(new Date());
        eventDTO.setLocation("Location");
        eventDTO.setDescription("Description");

        Set<ConstraintViolation<EventDTO>> violations = validator.validate(eventDTO);
        assertEquals(1, violations.size());

        ConstraintViolation<EventDTO> violation = violations.iterator().next();
        assertEquals("Event name cannot be null", violation.getMessage());
    }

    @Test
    public void whenNameIsTooShort_thenValidationFails() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("a");  // O nome deve ter pelo menos 3 caracteres
        eventDTO.setDate(new Date());
        eventDTO.setLocation("Location");
        eventDTO.setDescription("Description");

        Set<ConstraintViolation<EventDTO>> violations = validator.validate(eventDTO);
        assertEquals(1, violations.size());

        ConstraintViolation<EventDTO> violation = violations.iterator().next();
        assertEquals("Event name must have at least 3 characters", violation.getMessage());
    }

    @Test
    public void whenDateIsNull_thenValidationFails() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("Valid Name");
        eventDTO.setDate(null);  // A data não pode ser nula
        eventDTO.setLocation("Location");
        eventDTO.setDescription("Description");

        Set<ConstraintViolation<EventDTO>> violations = validator.validate(eventDTO);
        assertEquals(1, violations.size());

        ConstraintViolation<EventDTO> violation = violations.iterator().next();
        assertEquals("Date cannot be null", violation.getMessage());
    }

    @Test
    public void whenLocationIsNull_thenValidationFails() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("Valid Name");
        eventDTO.setDate(new Date());
        eventDTO.setLocation(null);  // A localização não pode ser nula
        eventDTO.setDescription("Description");

        Set<ConstraintViolation<EventDTO>> violations = validator.validate(eventDTO);
        assertEquals(1, violations.size());

        ConstraintViolation<EventDTO> violation = violations.iterator().next();
        assertEquals("Location cannot be null", violation.getMessage());
    }

    @Test
    public void whenDescriptionIsTooLong_thenValidationFails() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("Valid Name");
        eventDTO.setDate(new Date());
        eventDTO.setLocation("Location");
        eventDTO.setDescription("A".repeat(501));  // A descrição não pode exceder 500 caracteres

        Set<ConstraintViolation<EventDTO>> violations = validator.validate(eventDTO);
        assertEquals(1, violations.size());

        ConstraintViolation<EventDTO> violation = violations.iterator().next();
        assertEquals("Description cannot exceed 500 characters", violation.getMessage());
    }

    @Test
    public void whenAllFieldsAreValid_thenNoValidationErrors() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("Valid Name");
        eventDTO.setDate(new Date());
        eventDTO.setLocation("Valid Location");
        eventDTO.setDescription("Valid Description");

        Set<ConstraintViolation<EventDTO>> violations = validator.validate(eventDTO);
        assertTrue(violations.isEmpty());
    }
}