package ru.dreadblade.stockmarket.paymentservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.dreadblade.stockmarket.paymentservice.domain.Payment;
import ru.dreadblade.stockmarket.paymentservice.domain.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
public class PaymentResponseDTO {
    private Long id;
    private Long accountId;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private Instant createdAt;

    public static PaymentResponseDTO map(Payment payment) {
        return PaymentResponseDTO.builder()
                .id(payment.getId())
                .accountId(payment.getAccount().getId())
                .amount(payment.getAmount())
                .paymentStatus(payment.getPaymentStatus())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}
