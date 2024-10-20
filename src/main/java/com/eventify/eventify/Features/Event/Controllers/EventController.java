package com.eventify.eventify.Features.Event.Controllers;
import com.eventify.eventify.Features.Event.DTO.EventDTO;
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
    public EventDTO createEvent(@RequestBody EventDTO eventDTO) {
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

    // Conversão de DTO para entidade e vice-versa
    private EventDTO convertToDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setNome(event.getNome());
        dto.setData(event.getData().toString());
        dto.setLocal(event.getLocal());
        dto.setDescricao(event.getDescricao());
        dto.setOrganizadorId(event.getOrganizador().getId());
        return dto;
    }

    private Event convertToEntity(EventDTO dto) {
        Event event = new Event();
        event.setId(dto.getId());
        event.setNome(dto.getNome());
        event.setData(java.sql.Date.valueOf(dto.getData()));
        event.setLocal(dto.getLocal());
        event.setDescricao(dto.getDescricao());
        User organizador = new User();
        organizador.setId(dto.getOrganizadorId());
        event.setOrganizador(organizador);
        return event;
    }
}