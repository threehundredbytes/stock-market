package ru.dreadblade.stockmarket.notificationservice.api.model;

import java.math.BigDecimal;

public record NotificationResponseDTO(Long id, BigDecimal atPrice, Long stockId) {
}
