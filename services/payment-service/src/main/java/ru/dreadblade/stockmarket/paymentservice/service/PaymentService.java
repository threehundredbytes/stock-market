package ru.dreadblade.stockmarket.paymentservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dreadblade.stockmarket.paymentservice.event.PaymentSucceededIntegrationEvent;
import ru.dreadblade.stockmarket.paymentservice.event.bus.EventBus;
import ru.dreadblade.stockmarket.paymentservice.model.PaymentRequestDTO;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final EventBus eventBus;

    public void createPayment(PaymentRequestDTO requestDTO) {
        var event = PaymentSucceededIntegrationEvent.builder()
                .paymentAccountId(requestDTO.getAccountId())
                .paymentAmount(requestDTO.getAmount())
                .build();

        eventBus.publish("payments", event);
    }
}
