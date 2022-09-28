package ru.dreadblade.stockmarket.orderservice.api.model;

import ru.dreadblade.stockmarket.orderservice.domain.OrderType;

import java.math.BigDecimal;

public record OrderRequestDTO(
        Long stockId,
        Long accountId,
        BigDecimal pricePerStock,
        Long quantity,
        OrderType orderType
) {
}
