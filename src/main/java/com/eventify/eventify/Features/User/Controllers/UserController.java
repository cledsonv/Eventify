package com.eventify.eventify.Features.User.Controllers;

import com.eventify.eventify.Features.User.Dtos.LoginDTOResponse;
import com.eventify.eventify.Features.User.Dtos.UserDTO;
import com.eventify.eventify.Features.User.Dtos.UserDTOResponse;
import com.eventify.eventify.Features.User.Dtos.UserLoginDTO;
import com.eventify.eventify.Features.User.Entities.User;
import com.eventify.eventify.Features.User.Enum.Role;
import com.eventify.eventify.Features.User.Service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PutMapping("/update-role")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public UserDTOResponse updateUserRole(@RequestParam String email, @RequestParam Role role) {
        User user = userService.updateUserRole(email, role);
        return convertToDTO(user);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTOResponse register(@Valid @RequestBody UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        user.setRole(Role.PARTICIPANTE);
        User savedUser = userService.registerUser(user);
        return convertToDTO(savedUser);
    }

    @PostMapping("/login")
    public LoginDTOResponse login(@Valid @RequestBody UserLoginDTO loginDTO) {
        User user = userService.authenticate(loginDTO.getEmail(), loginDTO.getPassword());
        String token = userService.generateToken(user);
        return new LoginDTOResponse(token);
    }


    @GetMapping
    public List<UserDTOResponse> getUsers() {
        return userService.getUsers().stream().map(this::convertToDTO).toList();
    }

    @GetMapping("/{id}")
    public UserDTOResponse getUser(@PathVariable Long id) {
        User user = userService.getUser(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return convertToDTO(user);
    }

    @PutMapping("/{id}")
    public UserDTOResponse updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User updatedUser = userService.updateUser(id, user);
        return convertToDTO(updatedUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    private UserDTOResponse convertToDTO(User user) {
        return modelMapper.map(user, UserDTOResponse.class);
    }

    private User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}