package com.lorarch.challenge.service;

import com.lorarch.challenge.dto.UserDTO;
import com.lorarch.challenge.repository.RoleRepository;
import com.lorarch.challenge.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository users;
    private final RoleRepository roles;
    private final PasswordEncoder encoder;

    public UserService(UserRepository users, RoleRepository roles, PasswordEncoder encoder) {
        this.users = users;
        this.roles = roles;
        this.encoder = encoder;
    }

    @Transactional
    public void register(String username, String rawPassword, String defaultRole) {
        users.insertUserNative(username, encoder.encode(rawPassword), 1);
        Long userId = users.findIdByUsername(username);
        Long roleId = roles.findIdByName(defaultRole);
        if (userId == null) throw new IllegalStateException("Usuário não localizado após insert");
        if (roleId == null) throw new IllegalStateException("Role inexistente: " + defaultRole);
        roles.addUserRole(userId, roleId);
    }

    @Transactional
    public void registerNewUser(UserDTO dto) {
        register(dto.getUsername(), dto.getPassword(), "ROLE_OPERADOR");
    }
}