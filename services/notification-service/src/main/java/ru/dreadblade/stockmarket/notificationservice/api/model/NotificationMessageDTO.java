package ru.dreadblade.stockmarket.notificationservice.api.model;

import java.math.BigDecimal;

public record NotificationMessageDTO(String stockTicker, BigDecimal pricePerStock) {
}
