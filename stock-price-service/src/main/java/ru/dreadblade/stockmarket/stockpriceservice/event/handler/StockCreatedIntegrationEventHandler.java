package ru.dreadblade.stockmarket.stockpriceservice.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.stockpriceservice.domain.Stock;
import ru.dreadblade.stockmarket.stockpriceservice.event.StockCreatedIntegrationEvent;
import ru.dreadblade.stockmarket.stockpriceservice.repository.StockRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockCreatedIntegrationEventHandler implements IntegrationEventHandler<StockCreatedIntegrationEvent> {
    private final StockRepository stockRepository;

    @KafkaListener(groupId = "stock-created-group", topics = "stock-created")
    @Override
    public void handleIntegrationEvent(StockCreatedIntegrationEvent integrationEvent) {
        log.trace("Handling integration event: {} ({}): {}\n", integrationEvent.getId().toString(),
                integrationEvent.getClass().getSimpleName(), integrationEvent.getCreatedAt().toString());

        Stock stock = Stock.builder()
                .id(integrationEvent.getStockId())
                .price(integrationEvent.getStockPrice())
                .createdAt(integrationEvent.getStockCreatedAt())
                .build();

        stockRepository.save(stock);
    }
}
