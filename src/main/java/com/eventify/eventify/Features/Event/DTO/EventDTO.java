package com.eventify.eventify.Features.Event.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EventDTO {

    private Long id;

    @NotNull(message = "Event name cannot be null")
    @Size(min = 3, message = "Event name must have at least 3 characters")
    private String name;

    @NotNull(message = "Date cannot be null")
    private String date;  // Adjust to Date with proper formatting

    @NotNull(message = "Location cannot be null")
    private String location;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Organizer ID cannot be null")
    private Long organizerId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Event name cannot be null") @Size(min = 3, message = "Event name must have at least 3 characters") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "Event name cannot be null") @Size(min = 3, message = "Event name must have at least 3 characters") String name) {
        this.name = name;
    }

    public @NotNull(message = "Date cannot be null") String getDate() {
        return date;
    }

    public void setDate(@NotNull(message = "Date cannot be null") String date) {
        this.date = date;
    }

    public @NotNull(message = "Location cannot be null") String getLocation() {
        return location;
    }

    public void setLocation(@NotNull(message = "Location cannot be null") String location) {
        this.location = location;
    }

    public @Size(max = 500, message = "Description cannot exceed 500 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 500, message = "Description cannot exceed 500 characters") String description) {
        this.description = description;
    }

    public @NotNull(message = "Organizer ID cannot be null") Long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(@NotNull(message = "Organizer ID cannot be null") Long organizerId) {
        this.organizerId = organizerId;
    }
}

