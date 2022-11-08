package ru.dreadblade.stockmarket.accountservice.api.model;

import java.time.Instant;

public record StockOnAccountResponseDTO(Long stockId, Long quantity, Long reservedQuantity, Instant purchasedAt) {
}
