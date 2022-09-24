package ru.dreadblade.stockmarket.orderservice.event.handler;

public interface IntegrationEventHandler<T> {
    void handleIntegrationEvent(T integrationEvent);
}
