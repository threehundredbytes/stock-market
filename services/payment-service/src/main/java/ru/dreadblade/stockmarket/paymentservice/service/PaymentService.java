package ru.dreadblade.stockmarket.paymentservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.dreadblade.stockmarket.paymentservice.domain.Account;
import ru.dreadblade.stockmarket.paymentservice.domain.Payment;
import ru.dreadblade.stockmarket.paymentservice.domain.PaymentStatus;
import ru.dreadblade.stockmarket.paymentservice.event.PaymentCreatedIntegrationEvent;
import ru.dreadblade.stockmarket.paymentservice.event.bus.EventBus;
import ru.dreadblade.stockmarket.paymentservice.model.PaymentRequestDTO;
import ru.dreadblade.stockmarket.paymentservice.model.PaymentResponseDTO;
import ru.dreadblade.stockmarket.paymentservice.repository.AccountRepository;
import ru.dreadblade.stockmarket.paymentservice.repository.PaymentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;
    private final EventBus eventBus;

    public List<PaymentResponseDTO> findAllByAccountId(Long accountId, String userId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!userId.equals(account.getOwnerId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return paymentRepository.findAllByAccount(account).stream()
                .map(PaymentResponseDTO::map)
                .toList();
    }

    public PaymentResponseDTO createPayment(PaymentRequestDTO requestDTO, String userId) {
        Account account = accountRepository.findById(requestDTO.getAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!userId.equals(account.getOwnerId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        PaymentStatus paymentStatus = RandomUtils.nextInt(100) <= 75 ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;

        Payment payment = Payment.builder()
                .amount(requestDTO.getAmount())
                .account(account)
                .paymentStatus(paymentStatus)
                .build();

        payment = paymentRepository.save(payment);

        var event = PaymentCreatedIntegrationEvent.builder()
                .paymentAccountId(account.getId())
                .paymentAmount(payment.getAmount())
                .paymentStatus(payment.getPaymentStatus())
                .build();

        eventBus.publish("payment-created", event);

        return PaymentResponseDTO.map(payment);
    }
}
