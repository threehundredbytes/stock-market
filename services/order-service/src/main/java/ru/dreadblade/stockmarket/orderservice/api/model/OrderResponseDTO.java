package ru.dreadblade.stockmarket.orderservice.api.model;

import ru.dreadblade.stockmarket.orderservice.domain.OrderStatus;
import ru.dreadblade.stockmarket.orderservice.domain.OrderType;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderResponseDTO(
        Long id,
        BigDecimal pricePerStock,
        Long currentQuantity,
        Long initialQuantity,
        Long stockId,
        Long accountId,
        OrderType orderType,
        OrderStatus orderStatus,
        Instant closedAt,
        Instant createdAt
) {
}
