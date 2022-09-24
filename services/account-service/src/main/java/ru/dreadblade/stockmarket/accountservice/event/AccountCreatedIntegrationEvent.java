package ru.dreadblade.stockmarket.accountservice.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountCreatedIntegrationEvent extends IntegrationEvent {
    private Long accountId;
    private String ownerId;
}
