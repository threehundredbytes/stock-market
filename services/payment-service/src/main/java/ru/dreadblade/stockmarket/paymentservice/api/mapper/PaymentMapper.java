package ru.dreadblade.stockmarket.paymentservice.api.mapper;

import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.paymentservice.api.model.PaymentResponseDTO;
import ru.dreadblade.stockmarket.paymentservice.domain.Payment;

@Component
public class PaymentMapper {
    public PaymentResponseDTO mapEntityToResponseDTO(Payment payment) {
        return new PaymentResponseDTO(payment.getId(), payment.getAccount().getId(), payment.getAmount(),
                payment.getPaymentStatus(), payment.getCreatedAt());
    }
}
