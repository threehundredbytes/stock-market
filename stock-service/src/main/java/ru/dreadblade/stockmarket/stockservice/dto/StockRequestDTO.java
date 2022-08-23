package ru.dreadblade.stockmarket.stockservice.dto;

import java.math.BigDecimal;

public record StockRequestDTO(String name, String code, BigDecimal price) {
}
