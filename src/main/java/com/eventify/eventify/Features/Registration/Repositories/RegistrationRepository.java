package com.eventify.eventify.Features.Registration.Repositories;

import com.eventify.eventify.Features.Event.Entities.Event;
import com.eventify.eventify.Features.Registration.Entities.Registration;
import com.eventify.eventify.Features.User.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByUserAndEvent(User user, Event event);
    List<Registration> findAllByUser(User user);
}
