package ru.dreadblade.stockmarket.accountservice.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.accountservice.domain.Account;
import ru.dreadblade.stockmarket.accountservice.event.PaymentCreatedIntegrationEvent;
import ru.dreadblade.stockmarket.accountservice.repository.AccountRepository;

import java.math.BigDecimal;

import static ru.dreadblade.stockmarket.accountservice.event.model.PaymentStatus.SUCCESS;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentCreatedIntegrationEventHandler implements IntegrationEventHandler<PaymentCreatedIntegrationEvent> {

    private final AccountRepository accountRepository;

    @KafkaListener(groupId = "account-service-group", topics = "payment-created")
    @Override
    public void handleIntegrationEvent(PaymentCreatedIntegrationEvent integrationEvent) {
        log.trace("Handling integration event: {} ({}): {}", integrationEvent.getId().toString(),
                integrationEvent.getClass().getSimpleName(), integrationEvent.getCreatedAt().toString());

        if (SUCCESS.equals(integrationEvent.getPaymentStatus())) {
            Account account = accountRepository.findById(integrationEvent.getPaymentAccountId())
                    .orElseThrow(IllegalStateException::new);

            BigDecimal balance = account.getBalance().add(integrationEvent.getPaymentAmount());
            account.setBalance(balance);

            accountRepository.save(account);
        }
    }
}
