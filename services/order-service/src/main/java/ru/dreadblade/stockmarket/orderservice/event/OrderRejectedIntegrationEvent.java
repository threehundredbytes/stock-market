package ru.dreadblade.stockmarket.orderservice.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRejectedIntegrationEvent extends IntegrationEvent {
    private Long orderId;
}
