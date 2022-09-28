package ru.dreadblade.stockmarket.accountservice.api.model;

public record StockOnAccountResponseDTO(Long stockId, Long quantity, Long reservedQuantity) {
}
