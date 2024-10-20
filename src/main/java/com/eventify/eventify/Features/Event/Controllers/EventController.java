package com.eventify.eventify.Features.Event.Controllers;

import com.eventify.eventify.Core.Exception.RoleNotPermisionException;
import com.eventify.eventify.Features.Event.DTO.EventDTO;
import com.eventify.eventify.Features.Event.DTO.EventDTOResponse;
import com.eventify.eventify.Features.User.Dtos.UserDTOEvent;
import com.eventify.eventify.Features.User.Enum.Role;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.eventify.eventify.Features.Event.Entities.Event;
import com.eventify.eventify.Features.Event.Services.EventService;
import com.eventify.eventify.Features.User.Entities.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    private final ModelMapper modelMapper;

    public EventController(EventService eventService, ModelMapper modelMapper) {
        this.eventService = eventService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventDTOResponse createEvent(@Valid @RequestBody EventDTO eventDTO,  @AuthenticationPrincipal User user) {
        if (user.getRole() != Role.ORGANIZADOR && user.getRole() != Role.ADMIN) {
            throw new RoleNotPermisionException("Apenas organizadores podem criar eventos.");
        }
        Event event = convertToEntity(eventDTO);
        event.setOrganizer(user);
        Event savedEvent = eventService.createEvent(event);
        return convertToDTO(savedEvent);
    }

    @GetMapping
    public List<EventDTOResponse> listEvents() {
        return eventService.listEvents().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Optional<EventDTOResponse> getEvent(@PathVariable Long id) {
        return eventService.getEvent(id).map(this::convertToDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable Long id, @AuthenticationPrincipal User user) {
        eventService.deleteEvent(id, user);
    }

    @GetMapping("/{eventId}/users")
    public List<UserDTOEvent> listEventUsers(@PathVariable Long eventId) {
        List<User> users = eventService.listEventUsers(eventId);
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private UserDTOEvent convertToDTO(User user) {
        return modelMapper.map(user, UserDTOEvent.class);
    }

    private EventDTOResponse convertToDTO(Event event) {
        return modelMapper.map(event, EventDTOResponse.class);
    }

    private Event convertToEntity(EventDTO dto) {
        return modelMapper.map(dto, Event.class);
    }
}