package ru.dreadblade.stockmarket.notificationservice.events.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.notificationservice.domain.Stock;
import ru.dreadblade.stockmarket.notificationservice.events.StockPriceChangeIntegrationEvent;
import ru.dreadblade.stockmarket.notificationservice.api.model.NotificationMessageDTO;
import ru.dreadblade.stockmarket.notificationservice.repository.NotificationRepository;
import ru.dreadblade.stockmarket.notificationservice.repository.StockRepository;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockPriceChangeIntegrationEventHandler implements IntegrationEventHandler<StockPriceChangeIntegrationEvent> {
    private final NotificationRepository notificationRepository;
    private final StockRepository stockRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(groupId = "notification-service-group", topics = "stock-price-change")
    @Override
    public void handleIntegrationEvent(StockPriceChangeIntegrationEvent integrationEvent) {
        log.trace("Handling integration event: {} ({}): {}", integrationEvent.getId().toString(),
                integrationEvent.getClass().getSimpleName(), integrationEvent.getCreatedAt().toString());

        Long stockId = integrationEvent.getStockId();

        Stock stock = stockRepository.findById(stockId).orElseThrow(IllegalStateException::new);
        BigDecimal currentPrice = stock.getPrice();
        BigDecimal newPrice = integrationEvent.getNewPrice();

        notificationRepository.findAllActiveNotificationsByStockIdAndPriceBetween(stockId, currentPrice, newPrice)
                .forEach(notification -> {
                    var notificationMessageDTO = new NotificationMessageDTO(stock.getTicker(), notification.getAtPrice());

                    messagingTemplate.convertAndSendToUser(notification.getUserId(), "/topic/notifications/stocks/prices", notificationMessageDTO);

                    notification.setIsActive(false);
                    notificationRepository.save(notification);
                });
    }
}
