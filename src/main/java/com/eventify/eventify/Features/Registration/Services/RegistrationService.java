package com.eventify.eventify.Features.Registration.Services;

import com.eventify.eventify.Features.Registration.Entities.Registration;
import com.eventify.eventify.Features.Registration.Repositories.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    @Autowired
    private RegistrationRepository registrationRepository;

    public Registration registerToEvent(Registration registration) {
        return registrationRepository.save(registration);
    }

    public void cancelRegistration(Long id) {
        registrationRepository.deleteById(id);
    }

}