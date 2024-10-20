package com.eventify.eventify.Features.Registration.Controller;

import com.eventify.eventify.Features.Event.DTO.EventDTO;
import com.eventify.eventify.Features.Event.Entities.Event;
import com.eventify.eventify.Features.Registration.DTO.RegistrationDTO;
import com.eventify.eventify.Features.Registration.Entities.Registration;
import com.eventify.eventify.Features.Registration.Services.RegistrationService;
import com.eventify.eventify.Features.User.Entities.User;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    private final ModelMapper modelMapper;

    public RegistrationController(RegistrationService registrationService, ModelMapper modelMapper) {
        this.registrationService = registrationService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/user/{userId}/events")
    public List<EventDTO> listUserEvents(@PathVariable Long userId) {
        List<Event> events = registrationService.listUserEvents(userId);
        return events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/events/{eventId}/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationDTO registerToEvent(@PathVariable Long eventId, @AuthenticationPrincipal User user) {
        Registration savedRegistration = registrationService.registerToEvent(eventId, user);
        return convertToDTO(savedRegistration);
    }

    @DeleteMapping("/events/{eventId}/register/{registerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelRegistration(@PathVariable Long eventId, @PathVariable Long registerId, @AuthenticationPrincipal User authUser) {
        registrationService.cancelRegistration(eventId, registerId, authUser);
    }


    private RegistrationDTO convertToDTO(Registration registration) {
        return modelMapper.map(registration, RegistrationDTO.class);
    }

    private EventDTO convertToDTO(Event event) {
        return modelMapper.map(event, EventDTO.class);
    }
}
