package ru.dreadblade.stockmarket.stockpriceservice.event.handler;

public interface IntegrationEventHandler<T> {
    void handleIntegrationEvent(T integrationEvent);
}
