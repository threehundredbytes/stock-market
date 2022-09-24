package ru.dreadblade.stockmarket.orderservice.event.bus;

import ru.dreadblade.stockmarket.orderservice.event.IntegrationEvent;

public interface EventBus {
    void publish(String topic, IntegrationEvent event);
}
