package com.eventify.eventify.Features.User.Dtos;

public class UserDTOEvent {
    private Long id;
    private String name;
    private String role;

    public UserDTOEvent() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

}
