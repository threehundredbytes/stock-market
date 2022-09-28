package ru.dreadblade.stockmarket.userservice.api.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    CLIENT("client"),
    BUSINESS("business");

    private final String name;
}
