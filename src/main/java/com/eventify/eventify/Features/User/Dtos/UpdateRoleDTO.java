package com.eventify.eventify.Features.User.Dtos;

import com.eventify.eventify.Features.User.Enum.Role;

public record UpdateRoleDTO(
        String email,
        Role role
) {
}
