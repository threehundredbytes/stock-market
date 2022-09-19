package ru.dreadblade.stockmarket.accountservice.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.accountservice.domain.Account;
import ru.dreadblade.stockmarket.accountservice.event.PaymentSucceededIntegrationEvent;
import ru.dreadblade.stockmarket.accountservice.repository.AccountRepository;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentSucceededIntegrationEventHandler implements IntegrationEventHandler<PaymentSucceededIntegrationEvent> {

    private final AccountRepository accountRepository;

    @KafkaListener(groupId = "payments-account-service-group", topics = "payments")
    @Override
    public void handleIntegrationEvent(PaymentSucceededIntegrationEvent integrationEvent) {
        log.trace("Handling integration event: {} ({}): {}\n", integrationEvent.getId().toString(),
                integrationEvent.getClass().getSimpleName(), integrationEvent.getCreatedAt().toString());

        Account account = accountRepository.findById(integrationEvent.getPaymentAccountId()).orElseThrow();

        BigDecimal balance = account.getBalance().add(integrationEvent.getPaymentAmount());
        account.setBalance(balance);

        accountRepository.save(account);
    }
}
