package ru.dreadblade.stockmarket.stockpriceservice.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.stockpriceservice.model.StockPrice;
import ru.dreadblade.stockmarket.stockpriceservice.event.StockCreatedIntegrationEvent;
import ru.dreadblade.stockmarket.stockpriceservice.repository.StockRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockCreatedIntegrationEventHandler implements IntegrationEventHandler<StockCreatedIntegrationEvent> {
    private final StockRepository stockRepository;

    @KafkaListener(groupId = "{app.kafka.consumer.group}", topics = "${app.kafka.topic.stock-created}")
    @Override
    public void handleIntegrationEvent(StockCreatedIntegrationEvent integrationEvent) {
        log.trace("Handling integration event: {} ({}): {}", integrationEvent.getId().toString(),
                integrationEvent.getClass().getSimpleName(), integrationEvent.getCreatedAt().toString());

        StockPrice stock = StockPrice.builder()
                .stockId(integrationEvent.getStockId())
                .price(integrationEvent.getStockPrice())
                .build();

        stockRepository.save(stock);
    }
}
