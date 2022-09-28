package ru.dreadblade.stockmarket.notificationservice.api.model;

import java.math.BigDecimal;

public record NotificationRequestDTO(Long stockId, BigDecimal atPrice) {
}
