package com.eventify.eventify.Features.User.Controllers;

import com.eventify.eventify.Core.Exception.RoleNotPermisionException;
import com.eventify.eventify.Features.User.Dtos.*;
import com.eventify.eventify.Features.User.Entities.User;
import com.eventify.eventify.Features.User.Enum.Role;
import com.eventify.eventify.Features.User.Service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public UserDTOResponse updateUserRole(@RequestBody UpdateRoleDTO updateRoleDTO,  @AuthenticationPrincipal User user) {
        User userUpdate = userService.updateUserRole(updateRoleDTO.email(), updateRoleDTO.role(), user);
        return convertToDTO(userUpdate);
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
        String token =userService.singIn(loginDTO.getEmail(), loginDTO.getPassword());
        return new LoginDTOResponse(token);
    }


    @GetMapping
    public List<UserDTOResponse> getUsers(@AuthenticationPrincipal User user) {
        return userService.getUsers(user).stream().map(this::convertToDTO).toList();
    }

    @GetMapping("/{id}")
    public UserDTOResponse getUser(@PathVariable Long id, @AuthenticationPrincipal User authUser) {
        User user = userService.getUser(id, authUser);
        return convertToDTO(user);
    }

    @PutMapping("/{id}")
    public UserDTOResponse updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUser updateUser, @AuthenticationPrincipal User authUser) {
        User updatedUser = userService.updateUser(id, updateUser, authUser);
        return convertToDTO(updatedUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id, @AuthenticationPrincipal User authUser) {
        userService.deleteUser(id, authUser);
    }

    private UserDTOResponse convertToDTO(User user) {
        return modelMapper.map(user, UserDTOResponse.class);
    }

    private User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}