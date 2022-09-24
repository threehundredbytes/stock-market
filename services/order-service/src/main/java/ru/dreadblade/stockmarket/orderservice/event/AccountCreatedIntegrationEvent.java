package ru.dreadblade.stockmarket.orderservice.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreatedIntegrationEvent extends IntegrationEvent {
    private Long accountId;
    private String ownerId;
}
