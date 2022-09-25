package ru.dreadblade.stockmarket.paymentservice.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreatedIntegrationEvent extends IntegrationEvent {
    private Long accountId;
    private String ownerId;
}

