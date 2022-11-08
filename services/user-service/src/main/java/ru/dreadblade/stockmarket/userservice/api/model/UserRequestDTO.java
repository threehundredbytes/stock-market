package ru.dreadblade.stockmarket.userservice.api.model;

public record UserRequestDTO(String username, String password, Role role) {
}