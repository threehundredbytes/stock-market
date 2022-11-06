package ru.dreadblade.stockmarket.orderservice.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.orderservice.domain.Stock;
import ru.dreadblade.stockmarket.orderservice.event.StockPriceChangeIntegrationEvent;
import ru.dreadblade.stockmarket.orderservice.repository.StockRepository;
import ru.dreadblade.stockmarket.orderservice.service.TradeService;
import ru.dreadblade.stockmarket.shared.event.handler.IntegrationEventHandler;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockPriceChangeIntegrationEventHandler implements IntegrationEventHandler<StockPriceChangeIntegrationEvent> {
    private final StockRepository stockRepository;
    private final TradeService tradeService;

    @KafkaListener(groupId = "${app.kafka.consumer.group}", topics = "${app.kafka.topic.stock-price-change}")
    @Override
    public void handleIntegrationEvent(StockPriceChangeIntegrationEvent integrationEvent) {
        log.trace("Handling integration event: {} ({}): {}", integrationEvent.getId().toString(), integrationEvent.getClass().getSimpleName(), integrationEvent.getCreatedAt().toString());

        Stock stock = stockRepository.findById(integrationEvent.getStockId()).orElseThrow(IllegalStateException::new);

        BigDecimal currentStockPrice = stock.getPrice();
        BigDecimal newStockPrice = integrationEvent.getNewPrice();

        stock.setPrice(newStockPrice);
        stock = stockRepository.save(stock);

        tradeService.processOrders(stock, currentStockPrice, newStockPrice);
    }
}
