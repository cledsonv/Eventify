package com.eventify.eventify.Features.Registration.Services;

import com.eventify.eventify.Core.Exception.ItemNotFoundException;
import com.eventify.eventify.Features.Event.Entities.Event;
import com.eventify.eventify.Features.Event.Repositories.EventRepository;
import com.eventify.eventify.Features.Event.Services.EventService;
import com.eventify.eventify.Features.Registration.Entities.Registration;
import com.eventify.eventify.Features.Registration.Repositories.RegistrationRepository;
import com.eventify.eventify.Features.User.Entities.User;
import com.eventify.eventify.Features.User.Enum.Role;
import com.eventify.eventify.Features.User.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    public RegistrationService(RegistrationRepository registrationRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.registrationRepository = registrationRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public Registration registerToEvent(Long eventId, User user) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ItemNotFoundException("Evento não encontrado"));
        if (registrationRepository.existsByUserAndEvent(user, event)) {
            throw new IllegalStateException("Usuário já registrado no evento");
        }
        Registration registration = new Registration();
        registration.setEvent(event);
        registration.setUser(user);
        registration.setCreatedAt(LocalDate.now());
        return registrationRepository.save(registration);
    }

    public List<Event> listUserEvents(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ItemNotFoundException("Usuário não encontrado");
        }
        List<Registration> registrations = registrationRepository.findAllByUser(user.get());
        return registrations.stream().map(Registration::getEvent).collect(Collectors.toList());
    }


    public void cancelRegistration(Long eventId, Long registerId, User user) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new ItemNotFoundException("Evento não encontrado"));

        Registration registration = registrationRepository.findById(registerId)
                .orElseThrow(() -> new ItemNotFoundException("Registro não encontrado"));

        if (!registration.getUser().equals(user) && user.getRole() != Role.ADMIN) {
            throw new IllegalStateException("Usuário não tem permissão para cancelar este registro");
        }

        registrationRepository.deleteById(registerId);
    }

}