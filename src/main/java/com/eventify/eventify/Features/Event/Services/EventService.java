package com.eventify.eventify.Features.Event.Services;

import com.eventify.eventify.Features.Event.Entities.event;
import com.eventify.eventify.Features.Event.Repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public event createEvent(event event) {
        return eventRepository.save(event);
    }

    public List<event> listEvents() {
        return eventRepository.findAll();
    }

    public Optional<event> getEvent(Long id) {
        return eventRepository.findById(id);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}

