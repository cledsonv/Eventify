package com.eventify.eventify.Features.Event.Services;

import com.eventify.eventify.Core.Exception.ItemNotFoundException;
import com.eventify.eventify.Core.Exception.RoleNotPermisionException;
import com.eventify.eventify.Features.Event.Entities.Event;
import com.eventify.eventify.Features.Event.Repositories.EventRepository;
import com.eventify.eventify.Features.Registration.Entities.Registration;
import com.eventify.eventify.Features.User.Entities.User;
import com.eventify.eventify.Features.User.Enum.Role;
import com.eventify.eventify.Features.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    private final UserService userService;

    EventService(EventRepository eventRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> listEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEvent(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if(event.isEmpty()){
            throw new ItemNotFoundException("Evento não encontrado");
        }
        return event;
    }

    public void deleteEvent(Long id, User user) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new ItemNotFoundException("Evento não encontrado");
        }
        if (!user.getId().equals(event.get().getOrganizer().getId())  && user.getRole() != Role.ADMIN) {
            throw new RoleNotPermisionException("Apenas o organizador pode deletar o evento.");
        }
        eventRepository.deleteById(id);
    }

    public List<User> listEventUsers(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return event.getRegistrations().stream()
                .map(Registration::getUser)
                .collect(Collectors.toList());
    }
}

