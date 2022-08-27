package ru.dreadblade.stockmarket.stockservice.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record StockRequestDTO(String name, String ticker, BigDecimal price) {
}
