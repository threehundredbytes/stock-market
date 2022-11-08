package ru.dreadblade.stockmarket.accountservice.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.dreadblade.stockmarket.shared.event.IntegrationEvent;

@Getter
@Setter
@Builder
public class AccountCreatedIntegrationEvent extends IntegrationEvent {
    private Long accountId;
    private String ownerId;
}
