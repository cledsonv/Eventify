package com.eventify.eventify.Features.User.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @Email(message = "Email deve ser válido")
    @NotBlank(message = "Email é obrigatório")
    private String email;

    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    @NotBlank(message = "Senha é obrigatória")
    private String password;

    private String role;

    public UserDTO() {}

    public @NotBlank(message = "Nome é obrigatório") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Nome é obrigatório") String name) {
        this.name = name;
    }

    public @Email(message = "Email deve ser válido") @NotBlank(message = "Email é obrigatório") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email deve ser válido") @NotBlank(message = "Email é obrigatório") String email) {
        this.email = email;
    }

    public @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres") @NotBlank(message = "Senha é obrigatória") String getPassword() {
        return password;
    }

    public void setPassword(@Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres") @NotBlank(message = "Senha é obrigatória") String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}