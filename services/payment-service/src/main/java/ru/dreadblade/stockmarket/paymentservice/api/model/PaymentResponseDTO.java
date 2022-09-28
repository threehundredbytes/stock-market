package ru.dreadblade.stockmarket.paymentservice.api.model;

import ru.dreadblade.stockmarket.paymentservice.domain.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentResponseDTO(
        Long id,
        Long accountId,
        BigDecimal amount,
        PaymentStatus paymentStatus,
        Instant createdAt
) {
}
