package ru.dreadblade.stockmarket.accountservice.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.accountservice.config.KafkaTopics;
import ru.dreadblade.stockmarket.accountservice.domain.Account;
import ru.dreadblade.stockmarket.accountservice.domain.Stock;
import ru.dreadblade.stockmarket.accountservice.domain.StockOnAccount;
import ru.dreadblade.stockmarket.accountservice.event.OrderConfirmedIntegrationEvent;
import ru.dreadblade.stockmarket.accountservice.event.OrderCreatedIntegrationEvent;
import ru.dreadblade.stockmarket.accountservice.event.OrderRejectedIntegrationEvent;
import ru.dreadblade.stockmarket.accountservice.repository.AccountRepository;
import ru.dreadblade.stockmarket.accountservice.repository.StockOnAccountRepository;
import ru.dreadblade.stockmarket.accountservice.repository.StockRepository;
import ru.dreadblade.stockmarket.shared.event.bus.EventBus;
import ru.dreadblade.stockmarket.shared.event.handler.IntegrationEventHandler;

import java.math.BigDecimal;
import java.util.Optional;

import static ru.dreadblade.stockmarket.accountservice.event.model.OrderType.PURCHASE;
import static ru.dreadblade.stockmarket.accountservice.event.model.OrderType.SALE;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedIntegrationEventHandler implements IntegrationEventHandler<OrderCreatedIntegrationEvent> {
    private final AccountRepository accountRepository;
    private final StockOnAccountRepository stockOnAccountRepository;
    private final StockRepository stockRepository;
    private final EventBus eventBus;

    private final KafkaTopics kafkaTopics;

    @KafkaListener(groupId = "${app.kafka.consumer.group}", topics = "${app.kafka.topic.order-created}")
    @Override
    public void handleIntegrationEvent(OrderCreatedIntegrationEvent integrationEvent) {
        log.trace("Handling integration event: {} ({}): {}", integrationEvent.getId().toString(),
                integrationEvent.getClass().getSimpleName(), integrationEvent.getCreatedAt().toString());

        Account account = accountRepository.findById(integrationEvent.getAccountId()).orElseThrow(IllegalStateException::new);

        Long orderStockQuantity = integrationEvent.getStockQuantity();
        BigDecimal orderPrice = integrationEvent.getPricePerStock()
                .multiply(new BigDecimal(orderStockQuantity));
        String userId = account.getOwnerId();

        if (PURCHASE.equals(integrationEvent.getOrderType())) {
            BigDecimal reservedAccountBalance = account.getReservedBalance();

            if (account.getBalance().subtract(reservedAccountBalance).compareTo(orderPrice) >= 0) {
                account.setReservedBalance(reservedAccountBalance.add(orderPrice));

                accountRepository.save(account);

                eventBus.publish(kafkaTopics.getOrderConfirmed(), getOrderConfirmedIntegrationEvent(integrationEvent, orderPrice, userId));
            } else {
                eventBus.publish(kafkaTopics.getOrderRejected(), getOrderRejectedIntegrationEvent(integrationEvent, orderPrice, userId));
            }
        }

        if (SALE.equals(integrationEvent.getOrderType())) {
            Stock stock = stockRepository.findById(integrationEvent.getStockId()).orElseThrow(IllegalStateException::new);

            Optional<StockOnAccount> stockOnAccountOptional = stockOnAccountRepository.findByAccountAndStock(account, stock);

            if (stockOnAccountOptional.isEmpty()) {
                eventBus.publish(kafkaTopics.getOrderRejected(), getOrderRejectedIntegrationEvent(integrationEvent, orderPrice, userId));

                return;
            }

            StockOnAccount stockOnAccount = stockOnAccountOptional.get();

            Long stockQuantityAtAccount = stockOnAccount.getQuantity();
            if (stockQuantityAtAccount - stockOnAccount.getReservedQuantity() >= orderStockQuantity) {
                stockOnAccount.setQuantity(stockQuantityAtAccount - orderStockQuantity);
                stockOnAccount.setReservedQuantity(stockOnAccount.getReservedQuantity() + orderStockQuantity);

                stockOnAccountRepository.save(stockOnAccount);

                eventBus.publish(kafkaTopics.getOrderConfirmed(), getOrderConfirmedIntegrationEvent(integrationEvent, orderPrice, userId));
            } else {
                eventBus.publish(kafkaTopics.getOrderRejected(), getOrderRejectedIntegrationEvent(integrationEvent, orderPrice, userId));
            }
        }
    }

    private OrderConfirmedIntegrationEvent getOrderConfirmedIntegrationEvent(OrderCreatedIntegrationEvent integrationEvent, BigDecimal orderPrice, String userId) {
        return OrderConfirmedIntegrationEvent.builder()
                .orderId(integrationEvent.getOrderId())
                .orderPrice(orderPrice)
                .orderType(integrationEvent.getOrderType())
                .stockTicker(integrationEvent.getStockTicker())
                .userId(userId)
                .build();
    }

    private OrderRejectedIntegrationEvent getOrderRejectedIntegrationEvent(OrderCreatedIntegrationEvent integrationEvent, BigDecimal orderPrice, String userId) {
        return OrderRejectedIntegrationEvent.builder()
                .orderId(integrationEvent.getOrderId())
                .orderPrice(orderPrice)
                .orderType(integrationEvent.getOrderType())
                .stockTicker(integrationEvent.getStockTicker())
                .userId(userId)
                .build();
    }
}
