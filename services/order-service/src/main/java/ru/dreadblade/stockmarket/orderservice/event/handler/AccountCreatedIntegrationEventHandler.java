package ru.dreadblade.stockmarket.orderservice.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.orderservice.domain.Account;
import ru.dreadblade.stockmarket.orderservice.event.AccountCreatedIntegrationEvent;
import ru.dreadblade.stockmarket.orderservice.repository.AccountRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountCreatedIntegrationEventHandler implements IntegrationEventHandler<AccountCreatedIntegrationEvent> {
    private final AccountRepository accountRepository;

    @KafkaListener(groupId = "order-service-group", topics = "account-created")
    @Override
    public void handleIntegrationEvent(AccountCreatedIntegrationEvent integrationEvent) {
        log.trace("Handling integration event: {} ({}): {}\n", integrationEvent.getId().toString(),
                integrationEvent.getClass().getSimpleName(), integrationEvent.getCreatedAt().toString());

        Account account = Account.builder()
                .id(integrationEvent.getAccountId())
                .ownerId(integrationEvent.getOwnerId())
                .build();

        accountRepository.save(account);
    }
}