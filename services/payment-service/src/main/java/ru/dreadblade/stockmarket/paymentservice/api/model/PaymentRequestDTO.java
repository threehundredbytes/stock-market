package ru.dreadblade.stockmarket.paymentservice.api.model;

import java.math.BigDecimal;

public record PaymentRequestDTO(Long accountId, BigDecimal amount) {
}
