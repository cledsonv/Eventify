package com.eventify.eventify.Features.User.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserLoginDTO {

    @Email(message = "Email deve ser válido")
    @NotBlank(message = "Email é obrigatório")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    private String password;

    public @Email(message = "Email deve ser válido") @NotBlank(message = "Email é obrigatório") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email deve ser válido") @NotBlank(message = "Email é obrigatório") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Senha é obrigatória") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Senha é obrigatória") String password) {
        this.password = password;
    }
}