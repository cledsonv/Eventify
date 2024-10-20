package com.eventify.eventify.Features.Event.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eventify.eventify.Features.User;
import com.eventify.eventify.Features.Event.Entities.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOrganizador(User organizador);
}
