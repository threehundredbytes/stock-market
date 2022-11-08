package ru.dreadblade.stockmarket.orderservice.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.orderservice.domain.Stock;
import ru.dreadblade.stockmarket.orderservice.event.StockCreatedIntegrationEvent;
import ru.dreadblade.stockmarket.orderservice.repository.StockRepository;
import ru.dreadblade.stockmarket.shared.event.handler.IntegrationEventHandler;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockCreatedIntegrationEventHandler implements IntegrationEventHandler<StockCreatedIntegrationEvent> {
    private final StockRepository stockRepository;

    @KafkaListener(groupId = "${app.kafka.consumer.group}", topics = "${app.kafka.topic.stock-created}")
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
