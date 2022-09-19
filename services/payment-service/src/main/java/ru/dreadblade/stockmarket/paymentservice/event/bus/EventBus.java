package ru.dreadblade.stockmarket.paymentservice.event.bus;

import ru.dreadblade.stockmarket.paymentservice.event.IntegrationEvent;

public interface EventBus {
    void publish(String topic, IntegrationEvent event);
}
