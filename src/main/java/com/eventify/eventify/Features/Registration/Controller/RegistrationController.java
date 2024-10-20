package com.eventify.eventify.Features.Registration.Controller;

import com.eventify.eventify.Features.Registration.Entities.Registration;
import com.eventify.eventify.Features.Registration.Services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/events/{eventId}/register")
    public Registration registerToEvent(@PathVariable Long eventId, @RequestBody Registration registration) {
        return registrationService.registerToEvent(registration);
    }

    @DeleteMapping("/events/{eventId}/register/{id}")
    public void cancelRegistration(@PathVariable Long id) {
        registrationService.cancelRegistration(id);
    }
}
