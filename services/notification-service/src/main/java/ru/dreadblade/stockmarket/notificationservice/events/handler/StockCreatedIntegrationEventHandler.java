package ru.dreadblade.stockmarket.notificationservice.events.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.notificationservice.domain.Stock;
import ru.dreadblade.stockmarket.notificationservice.events.StockCreatedIntegrationEvent;
import ru.dreadblade.stockmarket.notificationservice.repository.StockRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockCreatedIntegrationEventHandler implements IntegrationEventHandler<StockCreatedIntegrationEvent> {
    private final StockRepository stockRepository;

    @KafkaListener(groupId = "notification-service-group", topics = "stock-created")
    @Override
    public void handleIntegrationEvent(StockCreatedIntegrationEvent integrationEvent) {
        log.trace("Handling integration event: {} ({}): {}", integrationEvent.getId().toString(),
                integrationEvent.getClass().getSimpleName(), integrationEvent.getCreatedAt().toString());

        Stock stock = Stock.builder()
                .id(integrationEvent.getStockId())
                .ticker(integrationEvent.getStockTicker())
                .price(integrationEvent.getStockPrice())
                .build();

        stockRepository.save(stock);
    }
}
