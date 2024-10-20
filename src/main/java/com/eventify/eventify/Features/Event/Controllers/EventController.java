package com.eventify.eventify.Features.Event.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.eventify.eventify.Features.Event.Entities.event;
import com.eventify.eventify.Features.Event.Services.EventService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping
    public event createEvent(@RequestBody event event) {
        return eventService.createEvent(event);
    }

    @GetMapping
    public List<event> listEvents() {
        return eventService.listEvents();
    }

    @GetMapping("/{id}")
    public Optional<event> getEvent(@PathVariable Long id) {
        return eventService.getEvent(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}
