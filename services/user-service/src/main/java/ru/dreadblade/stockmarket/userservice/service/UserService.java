package ru.dreadblade.stockmarket.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dreadblade.stockmarket.userservice.model.Role;
import ru.dreadblade.stockmarket.userservice.model.UserRequestDTO;

@Service
@RequiredArgsConstructor
public class UserService {
    private final KeycloakService keycloakService;

    public void createUser(UserRequestDTO userRequestDTO) {
        Role role = userRequestDTO.getRole();

        role = role == null ? Role.CLIENT : userRequestDTO.getRole();

        keycloakService.createUser(userRequestDTO.getUsername(), userRequestDTO.getPassword(), role.getName());
    }
}
