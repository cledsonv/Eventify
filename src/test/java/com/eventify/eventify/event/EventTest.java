package com.eventify.eventify.event;

import com.eventify.eventify.Features.Event.Entities.Event;
import com.eventify.eventify.Features.Registration.Entities.Registration;
import com.eventify.eventify.Features.User.Entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    private Event event;
    private User organizer;
    private Registration registration;

    @BeforeEach
    void setUp() {
        event = new Event();
        organizer = new User();
        registration = new Registration();
    }

    @Test
    void testSetAndGetId() {
        event.setId(1L);
        assertEquals(1L, event.getId());
    }

    @Test
    void testSetAndGetName() {
        event.setName("Sample Event");
        assertEquals("Sample Event", event.getName());
    }

    @Test
    void testSetAndGetDate() {
        Date now = new Date();
        event.setDate(now);
        assertEquals(now, event.getDate());
    }

    @Test
    void testSetAndGetLocation() {
        event.setLocation("Sample Location");
        assertEquals("Sample Location", event.getLocation());
    }

    @Test
    void testSetAndGetDescription() {
        event.setDescription("Event Description");
        assertEquals("Event Description", event.getDescription());
    }

    @Test
    void testSetAndGetOrganizer() {
        organizer.setId(1L);
        organizer.setName("John Doe");

        event.setOrganizer(organizer);

        assertNotNull(event.getOrganizer());
        assertEquals(1L, event.getOrganizer().getId());
        assertEquals("John Doe", event.getOrganizer().getName());
    }

    @Test
    void testSetAndGetRegistrations() {
        registration.setId(1L);
        registration.setEvent(event);

        event.setRegistrations(List.of(registration));

        assertNotNull(event.getRegistrations());
        assertEquals(1, event.getRegistrations().size());
        assertEquals(1L, event.getRegistrations().get(0).getId());
        assertEquals(event, event.getRegistrations().get(0).getEvent());
    }
}

