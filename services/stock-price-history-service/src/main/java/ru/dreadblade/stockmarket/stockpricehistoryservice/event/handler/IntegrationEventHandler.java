package ru.dreadblade.stockmarket.stockpricehistoryservice.event.handler;

public interface IntegrationEventHandler<T> {
    void handleIntegrationEvent(T integrationEvent);
}
