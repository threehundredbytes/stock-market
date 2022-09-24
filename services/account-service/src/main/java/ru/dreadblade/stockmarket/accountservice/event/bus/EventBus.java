package ru.dreadblade.stockmarket.accountservice.event.bus;

import ru.dreadblade.stockmarket.accountservice.event.IntegrationEvent;

public interface EventBus {
    void publish(String topic, IntegrationEvent event);
}
