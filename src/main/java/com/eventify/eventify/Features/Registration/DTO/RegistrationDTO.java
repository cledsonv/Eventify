package com.eventify.eventify.Features.Registration.DTO;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;

public class RegistrationDTO {

    private Long id;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Event ID cannot be null")
    private Long eventId;

    @NotNull(message = "Registration date cannot be null")
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "User ID cannot be null") Long getUserId() {
        return userId;
    }

    public void setUserId(@NotNull(message = "User ID cannot be null") Long userId) {
        this.userId = userId;
    }

    public @NotNull(message = "Event ID cannot be null") Long getEventId() {
        return eventId;
    }

    public void setEventId(@NotNull(message = "Event ID cannot be null") Long eventId) {
        this.eventId = eventId;
    }

    public @NotNull(message = "Registration date cannot be null") Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull(message = "Registration date cannot be null") Date createdAt) {
        this.createdAt = createdAt;
    }
}
