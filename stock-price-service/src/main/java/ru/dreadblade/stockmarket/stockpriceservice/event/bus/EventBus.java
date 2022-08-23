package ru.dreadblade.stockmarket.stockpriceservice.event.bus;

import ru.dreadblade.stockmarket.stockpriceservice.event.IntegrationEvent;

public interface EventBus {
    void publish(String topic, IntegrationEvent event);
}
