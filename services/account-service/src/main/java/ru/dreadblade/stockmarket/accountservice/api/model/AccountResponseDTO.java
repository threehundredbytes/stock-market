package ru.dreadblade.stockmarket.accountservice.api.model;

import java.math.BigDecimal;

public record AccountResponseDTO(Long id, BigDecimal balance, BigDecimal reservedBalance) {
}
