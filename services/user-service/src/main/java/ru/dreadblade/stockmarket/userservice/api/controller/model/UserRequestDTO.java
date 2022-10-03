package ru.dreadblade.stockmarket.userservice.api.controller.model;

public record UserRequestDTO(String username, String password, Role role) {
}