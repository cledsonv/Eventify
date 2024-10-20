package com.eventify.eventify.Features.Registration.Controller;

import com.eventify.eventify.Features.Event.Entities.Event;
import com.eventify.eventify.Features.Registration.DTO.RegistrationDTO;
import com.eventify.eventify.Features.Registration.Entities.Registration;
import com.eventify.eventify.Features.Registration.Services.RegistrationService;
import com.eventify.eventify.Features.User.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/events/{eventId}/register")
    public RegistrationDTO registerToEvent(@PathVariable Long eventId, @RequestBody RegistrationDTO registrationDTO) {
        Registration registration = convertToEntity(registrationDTO);
        Registration savedRegistration = registrationService.registerToEvent(registration);
        return convertToDTO(savedRegistration);
    }

    @DeleteMapping("/events/{eventId}/register/{id}")
    public void cancelRegistration(@PathVariable Long id) {
        registrationService.cancelRegistration(id);
    }

    // Conversão de DTO para entidade e vice-versa
    private RegistrationDTO convertToDTO(Registration registration) {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setId(registration.getId());
        dto.setUsuarioId(registration.getUsuario().getId());
        dto.setEventoId(registration.getEvento().getId());
        dto.setDataInscricao(registration.getDataInscricao().toString());
        return dto;
    }

    private Registration convertToEntity(RegistrationDTO dto) {
        Registration registration = new Registration();
        registration.setId(dto.getId());
        User user = new User();
        user.setId(dto.getUsuarioId());
        registration.setUsuario(user);
        Event event = new Event();
        event.setId(dto.getEventoId());
        registration.setEvento(event);
        registration.setDataInscricao(java.sql.Date.valueOf(dto.getDataInscricao()));
        return registration;
    }
}
