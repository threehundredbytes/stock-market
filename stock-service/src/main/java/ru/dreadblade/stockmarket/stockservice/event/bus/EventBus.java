package ru.dreadblade.stockmarket.stockservice.event.bus;

import ru.dreadblade.stockmarket.stockservice.event.IntegrationEvent;

public interface EventBus {
    void publish(String topic, IntegrationEvent event);
}
