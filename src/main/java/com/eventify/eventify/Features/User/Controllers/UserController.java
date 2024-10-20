package com.eventify.eventify.Features.User.Controllers;

import com.eventify.eventify.Features.User.DTO.UserDTO;
import com.eventify.eventify.Features.User.Entities.User;
import com.eventify.eventify.Features.User.Enum.Role;
import com.eventify.eventify.Features.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserDTO register(@RequestBody UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User savedUser = userService.registerUser(user);
        return convertToDTO(savedUser);
    }

    @GetMapping("/{id}")
    public Optional<UserDTO> getUser(@PathVariable Long id) {
        return userService.getUser(id).map(this::convertToDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    // Conversão de DTO para entidade e vice-versa
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setNome(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPapel(user.getRole().toString());
        return dto;
    }

    private User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setRole(Role.valueOf(dto.getPapel()));
        return user;
    }
}