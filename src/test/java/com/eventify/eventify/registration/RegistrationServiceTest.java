package com.eventify.eventify.registration;

import com.eventify.eventify.Core.Exception.ItemNotFoundException;
import com.eventify.eventify.Features.Event.Entities.Event;
import com.eventify.eventify.Features.Event.Repositories.EventRepository;
import com.eventify.eventify.Features.Registration.Entities.Registration;
import com.eventify.eventify.Features.Registration.Repositories.RegistrationRepository;
import com.eventify.eventify.Features.Registration.Services.RegistrationService;
import com.eventify.eventify.Features.User.Entities.User;
import com.eventify.eventify.Features.User.Enum.Role;
import com.eventify.eventify.Features.User.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegistrationServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegistrationService registrationService;

    private User user;
    private Event event;
    private Registration registration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setRole(Role.PARTICIPANTE);

        event = new Event();
        event.setId(1L);

        registration = new Registration();
        registration.setUser(user);
        registration.setEvent(event);
        registration.setCreatedAt(new Date());
    }

    @Test
    void testRegisterToEventSuccessfully() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
        when(registrationRepository.existsByUserAndEvent(any(User.class), any(Event.class))).thenReturn(false);
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        Registration savedRegistration = registrationService.registerToEvent(1L, user);

        assertNotNull(savedRegistration);
        assertEquals(user, savedRegistration.getUser());
        assertEquals(event, savedRegistration.getEvent());

        verify(eventRepository, times(1)).findById(1L);
        verify(registrationRepository, times(1)).save(any(Registration.class));
    }

    @Test
    void testRegisterToEventAlreadyRegistered() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
        when(registrationRepository.existsByUserAndEvent(any(User.class), any(Event.class))).thenReturn(true);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            registrationService.registerToEvent(1L, user);
        });

        assertEquals("Usuário já esta registrado no evento", exception.getMessage());
        verify(registrationRepository, never()).save(any(Registration.class));
    }

    @Test
    void testRegisterToEventEventNotFound() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());

        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () -> {
            registrationService.registerToEvent(1L, user);
        });

        assertEquals("Evento não encontrado", exception.getMessage());
        verify(registrationRepository, never()).save(any(Registration.class));
    }

    @Test
    void testListUserEvents() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(registrationRepository.findAllByUser(any(User.class))).thenReturn(List.of(registration));

        List<Event> events = registrationService.listUserEvents(1L);

        assertEquals(1, events.size());
        assertEquals(event, events.get(0));

        verify(userRepository, times(1)).findById(1L);
        verify(registrationRepository, times(1)).findAllByUser(any(User.class));
    }

    @Test
    void testListUserEventsUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () -> {
            registrationService.listUserEvents(1L);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    void testCancelRegistrationSuccessfully() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
        when(registrationRepository.findById(anyLong())).thenReturn(Optional.of(registration));

        registrationService.cancelRegistration(1L, 1L, user);

        verify(registrationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testCancelRegistrationEventNotFound() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());

        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () -> {
            registrationService.cancelRegistration(1L, 1L, user);
        });

        assertEquals("Evento não encontrado", exception.getMessage());
        verify(registrationRepository, never()).deleteById(anyLong());
    }

    @Test
    void testCancelRegistrationUnauthorized() {
        User otherUser = new User();
        otherUser.setId(2L);
        otherUser.setRole(Role.PARTICIPANTE);

        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
        when(registrationRepository.findById(anyLong())).thenReturn(Optional.of(registration));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            registrationService.cancelRegistration(1L, 1L, otherUser);
        });

        assertEquals("Usuário não tem permissão para cancelar este registro", exception.getMessage());
        verify(registrationRepository, never()).deleteById(anyLong());
    }
}
