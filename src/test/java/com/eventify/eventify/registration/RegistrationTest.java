package com.eventify.eventify.registration;

import com.eventify.eventify.Features.Event.Entities.Event;
import com.eventify.eventify.Features.Registration.Entities.Registration;
import com.eventify.eventify.Features.User.Entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationTest {

    private Registration registration;
    private User user;
    private Event event;

    @BeforeEach
    void setUp() {
        registration = new Registration();
        user = new User();
        event = new Event();
    }

    @Test
    void testSetAndGetId() {
        registration.setId(1L);
        assertEquals(1L, registration.getId());
    }

    @Test
    void testSetAndGetUser() {
        user.setId(1L);
        user.setName("John Doe");

        registration.setUser(user);

        assertNotNull(registration.getUser());
        assertEquals(1L, registration.getUser().getId());
        assertEquals("John Doe", registration.getUser().getName());
    }

    @Test
    void testSetAndGetEvent() {
        event.setId(1L);
        event.setName("Sample Event");

        registration.setEvent(event);

        assertNotNull(registration.getEvent());
        assertEquals(1L, registration.getEvent().getId());
        assertEquals("Sample Event", registration.getEvent().getName());
    }

    @Test
    void testSetAndGetCreatedAt() {
        Date now = new Date();
        registration.setCreatedAt(now);

        assertEquals(now, registration.getCreatedAt());
    }

    @Test
    void testRegistrationInitialization() {
        registration.setId(1L);
        registration.setUser(user);
        registration.setEvent(event);
        registration.setCreatedAt(new Date());

        assertNotNull(registration.getUser());
        assertNotNull(registration.getEvent());
        assertNotNull(registration.getCreatedAt());
        assertEquals(1L, registration.getId());
    }
}

