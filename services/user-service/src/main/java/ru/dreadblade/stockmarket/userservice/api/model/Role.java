package ru.dreadblade.stockmarket.userservice.api.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    CLIENT("client"),
    BUSINESS("business");

    private final String name;
}
