package com.eventify.eventify.Features.User.Service;

import com.eventify.eventify.Core.Exception.ItemNotFoundException;
import com.eventify.eventify.Core.Exception.RoleNotPermisionException;
import com.eventify.eventify.Core.Security.JwtUtil;
import com.eventify.eventify.Features.User.Dtos.UpdateUser;
import com.eventify.eventify.Features.User.Entities.User;
import com.eventify.eventify.Features.User.Enum.Role;
import com.eventify.eventify.Features.User.Repositories.UserRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUserRole(String email, Role role, User user) {
        if (user.getRole() != Role.ADMIN) {
            throw new RoleNotPermisionException("Apenas administradores podem atualizar o papel do usuário.");
        }
        User userUpdate = userRepository.findByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Usuário não encontrado"));
        userUpdate.setRole(role);
        return userRepository.save(userUpdate);
    }

    public List<User> getUsers(User user) {
        if (user.getRole() != Role.ADMIN) {
            throw new RoleNotPermisionException("Apenas administradores podem ver a lista de usuário.");
        }
        return userRepository.findAll();
    }


    public String singIn(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ConstraintViolationException("Credenciais inválidas", null));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ConstraintViolationException("Credenciais inválidas", null);
        }
        return jwtUtil.generateToken(user);
    }

    public User getUser(Long id, User authUser) {
        if (authUser.getRole() != Role.ADMIN) {
            throw new RoleNotPermisionException("Apenas administradores podem ver o usuário.");
        }
        return userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Usuário não encontrado"));
    }

    public User updateUser(Long id, UpdateUser updatedUser, User authUser) {
        if (!id.equals(authUser.getId()) && authUser.getRole() != Role.ADMIN) {
            throw new RoleNotPermisionException("Somente o usuario pode alterar seus dados.");
        }
        User user = userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Usuário não encontrado"));
        user.setName(updatedUser.name());
        return userRepository.save(user);
    }

    public void deleteUser(Long id, User authUser) {
        if (authUser.getRole() != Role.ADMIN) {
            throw new RoleNotPermisionException("Apenas administradores podem deletar usuários.");
        }
    Optional<User> user =   userRepository.findById(id);
    if(user.isEmpty()){
        throw new ItemNotFoundException("Usuário não encontrado");
    }
       userRepository.deleteById(id);
    }
}