package ru.dreadblade.stockmarket.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dreadblade.stockmarket.userservice.api.controller.model.Role;
import ru.dreadblade.stockmarket.userservice.api.controller.model.UserRequestDTO;

@Service
@RequiredArgsConstructor
public class UserService {
    private final KeycloakService keycloakService;

    public void createUser(UserRequestDTO userRequestDTO) {
        Role role = userRequestDTO.role();
        role = role == null ? Role.CLIENT : userRequestDTO.role();

        keycloakService.createUser(userRequestDTO.username(), userRequestDTO.password(), role.getName());
    }
}
