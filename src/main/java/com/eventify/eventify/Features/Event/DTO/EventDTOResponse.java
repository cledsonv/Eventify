package com.eventify.eventify.Features.Event.DTO;

import java.util.Date;

public class EventDTOResponse {

    private Long id;
    private String name;
    private String description;
    private Date date;
    private String location;
    private String organizerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public String getOrganizer() {
        return organizerId;
    }

    public void setOrganizer(String organizer) {
        this.organizerId = organizer;
    }

}
