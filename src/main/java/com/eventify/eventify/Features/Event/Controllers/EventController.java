package com.eventify.eventify.Features.Event.Controllers;
import com.eventify.eventify.Features.Event.DTO.EventDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private EventService eventService;

    @PostMapping
    public EventDTO createEvent(@Valid @RequestBody EventDTO eventDTO) {
        Event event = convertToEntity(eventDTO);
        Event savedEvent = eventService.createEvent(event);
        return convertToDTO(savedEvent);
    }

    @GetMapping
    public List<EventDTO> listEvents() {
        return eventService.listEvents().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Optional<EventDTO> getEvent(@PathVariable Long id) {
        return eventService.getEvent(id).map(this::convertToDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    // Conversion between DTO and Entity
    private EventDTO convertToDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setDate(event.getDate().toString());
        dto.setLocation(event.getLocation());
        dto.setDescription(event.getDescription());
        dto.setOrganizerId(event.getOrganizer().getId());
        return dto;
    }

    private Event convertToEntity(EventDTO dto) {
        Event event = new Event();
        event.setId(dto.getId());
        event.setName(dto.getName());
        event.setDate(java.sql.Date.valueOf(dto.getDate()));
        event.setLocation(dto.getLocation());
        event.setDescription(dto.getDescription());
        User organizer = new User();
        organizer.setId(dto.getOrganizerId());
        event.setOrganizer(organizer);
        return event;
    }
}