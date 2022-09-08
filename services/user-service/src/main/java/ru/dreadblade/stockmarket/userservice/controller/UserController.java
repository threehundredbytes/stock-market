package ru.dreadblade.stockmarket.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dreadblade.stockmarket.userservice.model.UserRequestDTO;
import ru.dreadblade.stockmarket.userservice.service.UserService;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/signup")
    public void signUp(@RequestBody UserRequestDTO userRequestDTO) {
        userService.createUser(userRequestDTO);
    }
}
