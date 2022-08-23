package ru.dreadblade.stockmarket.stockservice.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.stockservice.domain.Stock;
import ru.dreadblade.stockmarket.stockservice.event.StockPriceChangeIntegrationEvent;
import ru.dreadblade.stockmarket.stockservice.repository.StockRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockPriceChangeIntegrationEventHandler implements IntegrationEventHandler<StockPriceChangeIntegrationEvent> {
    private final StockRepository stockRepository;

    @KafkaListener(groupId = "stock-price-changes-group", topics = "stock-price-changes")
    @Override
    public void handleIntegrationEvent(StockPriceChangeIntegrationEvent integrationEvent) {
        log.trace("Handling integration event: {} ({}): {}\n", integrationEvent.getId().toString(),
                integrationEvent.getClass().getSimpleName(), integrationEvent.getCreatedAt().toString());

        Stock stock = stockRepository.findById(integrationEvent.getStockId()).orElseThrow();

        stock.setPrice(integrationEvent.getNewPrice());
        stock.setUpdatedAt(integrationEvent.getChangedAt());

        stockRepository.save(stock);
    }
}
