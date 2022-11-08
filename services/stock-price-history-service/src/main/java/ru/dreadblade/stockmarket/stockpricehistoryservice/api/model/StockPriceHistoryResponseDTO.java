package ru.dreadblade.stockmarket.stockpricehistoryservice.api.model;

import java.math.BigDecimal;
import java.time.Instant;

public record StockPriceHistoryResponseDTO(BigDecimal price, Instant at) {
}
