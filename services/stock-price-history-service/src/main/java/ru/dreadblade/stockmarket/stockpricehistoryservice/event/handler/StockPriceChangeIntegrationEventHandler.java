package ru.dreadblade.stockmarket.stockpricehistoryservice.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.stockpricehistoryservice.domain.Stock;
import ru.dreadblade.stockmarket.stockpricehistoryservice.event.StockPriceChangeIntegrationEvent;
import ru.dreadblade.stockmarket.stockpricehistoryservice.repository.StockRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockPriceChangeIntegrationEventHandler implements IntegrationEventHandler<StockPriceChangeIntegrationEvent> {
    private final StockRepository stockRepository;

    @KafkaListener(groupId = "stock-price-history-service-group", topics = "stock-price-change")
    @Override
    public void handleIntegrationEvent(StockPriceChangeIntegrationEvent integrationEvent) {
        log.trace("Handling integration event: {} ({}): {}", integrationEvent.getId().toString(),
                integrationEvent.getClass().getSimpleName(), integrationEvent.getCreatedAt().toString());

        Stock stock = Stock.builder()
                .id(integrationEvent.getStockId())
                .price(integrationEvent.getNewPrice())
                .createdAt(integrationEvent.getChangedAt())
                .build();

        stockRepository.save(stock);
    }
}
