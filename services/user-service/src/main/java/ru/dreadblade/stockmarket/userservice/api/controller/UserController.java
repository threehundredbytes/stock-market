package ru.dreadblade.stockmarket.userservice.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.dreadblade.stockmarket.userservice.api.model.UserRequestDTO;
import ru.dreadblade.stockmarket.userservice.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody UserRequestDTO userRequestDTO) {
        userService.createUser(userRequestDTO);
    }
}
