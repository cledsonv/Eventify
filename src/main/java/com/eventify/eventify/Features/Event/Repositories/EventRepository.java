package com.eventify.eventify.Features.Event.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eventify.eventify.Features.User.Entities.User;
import com.eventify.eventify.Features.Event.Entities.event;

import java.util.List;

public interface EventRepository extends JpaRepository<event, Long> {
    List<event> findByOrganizador(User organizador);
}
