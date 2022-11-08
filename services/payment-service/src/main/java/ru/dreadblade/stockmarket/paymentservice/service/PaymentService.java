package ru.dreadblade.stockmarket.paymentservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.dreadblade.stockmarket.paymentservice.api.mapper.PaymentMapper;
import ru.dreadblade.stockmarket.paymentservice.api.model.PaymentRequestDTO;
import ru.dreadblade.stockmarket.paymentservice.api.model.PaymentResponseDTO;
import ru.dreadblade.stockmarket.paymentservice.config.KafkaTopics;
import ru.dreadblade.stockmarket.paymentservice.domain.Account;
import ru.dreadblade.stockmarket.paymentservice.domain.Payment;
import ru.dreadblade.stockmarket.paymentservice.domain.PaymentStatus;
import ru.dreadblade.stockmarket.paymentservice.event.PaymentCreatedIntegrationEvent;
import ru.dreadblade.stockmarket.paymentservice.repository.AccountRepository;
import ru.dreadblade.stockmarket.paymentservice.repository.PaymentRepository;
import ru.dreadblade.stockmarket.shared.event.bus.EventBus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;

    private final KafkaTopics kafkaTopics;
    private final EventBus eventBus;

    private final PaymentMapper paymentMapper;

    public List<PaymentResponseDTO> findAllByAccountId(Long accountId, String userId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!userId.equals(account.getOwnerId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return paymentRepository.findAllByAccount(account).stream()
                .map(paymentMapper::mapEntityToResponseDTO)
                .toList();
    }

    public PaymentResponseDTO createPayment(PaymentRequestDTO requestDTO, String userId) {
        Account account = accountRepository.findById(requestDTO.accountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!userId.equals(account.getOwnerId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        PaymentStatus paymentStatus = RandomUtils.nextInt(100) <= 75 ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;

        Payment payment = Payment.builder()
                .amount(requestDTO.amount())
                .account(account)
                .paymentStatus(paymentStatus)
                .build();

        payment = paymentRepository.save(payment);

        var event = PaymentCreatedIntegrationEvent.builder()
                .paymentAccountId(account.getId())
                .paymentAmount(payment.getAmount())
                .paymentStatus(payment.getPaymentStatus())
                .build();

        eventBus.publish(kafkaTopics.getPaymentCreated(), event);

        return paymentMapper.mapEntityToResponseDTO(payment);
    }
}
