package com.eventify.eventify.registration;

import com.eventify.eventify.Features.Event.DTO.EventDTO;
import com.eventify.eventify.Features.Event.Entities.Event;
import com.eventify.eventify.Features.Registration.Controller.RegistrationController;
import com.eventify.eventify.Features.Registration.DTO.RegistrationDTO;
import com.eventify.eventify.Features.Registration.Entities.Registration;
import com.eventify.eventify.Features.Registration.Services.RegistrationService;
import com.eventify.eventify.Features.User.Entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RegistrationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RegistrationService registrationService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RegistrationController registrationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    void testListUserEvents() throws Exception {
        List<Event> events = List.of(new Event());
        when(registrationService.listUserEvents(anyLong())).thenReturn(events);
        when(modelMapper.map(any(Event.class), eq(EventDTO.class))).thenReturn(new EventDTO());

        mockMvc.perform(get("/registrations/user/1/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());

        verify(registrationService, times(1)).listUserEvents(anyLong());
    }

    @Test
    void testRegisterToEvent() throws Exception {
        User user = new User();
        Registration registration = new Registration();

        when(registrationService.registerToEvent(anyLong(), any(User.class))).thenReturn(registration);
        when(modelMapper.map(any(Registration.class), eq(RegistrationDTO.class))).thenReturn(new RegistrationDTO());

        mockMvc.perform(post("/registrations/events/1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(() -> "user"))  // Simula o usuário autenticado
                .andExpect(status().isCreated());

        verify(registrationService, times(1)).registerToEvent(anyLong(), any(User.class));
    }

    @Test
    void testCancelRegistration() throws Exception {
        User user = new User();

        mockMvc.perform(delete("/registrations/events/1/register/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(() -> "user"))  // Simula o usuário autenticado
                .andExpect(status().isNoContent());

        verify(registrationService, times(1)).cancelRegistration(anyLong(), anyLong(), any(User.class));
    }
}
