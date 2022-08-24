package ru.dreadblade.stockmarket.stockpricehistoryservice.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
public record StockPriceHistoryResponseDTO(BigDecimal price, Instant at) {
}
