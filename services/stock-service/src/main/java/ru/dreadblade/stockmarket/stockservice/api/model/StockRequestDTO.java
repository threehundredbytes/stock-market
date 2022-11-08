package ru.dreadblade.stockmarket.stockservice.api.model;

import java.math.BigDecimal;

public record StockRequestDTO(String name, String ticker, BigDecimal price) {
}
