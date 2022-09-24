package ru.dreadblade.stockmarket.accountservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderRejectedIntegrationEvent extends IntegrationEvent {
    private Long orderId;
}
