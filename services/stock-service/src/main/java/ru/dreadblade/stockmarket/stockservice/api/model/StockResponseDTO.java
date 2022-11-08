package ru.dreadblade.stockmarket.stockservice.api.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record StockResponseDTO(Long id, String name, String ticker, BigDecimal price) {
}
