package ru.dreadblade.stockmarket.accountservice.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.accountservice.domain.Account;
import ru.dreadblade.stockmarket.accountservice.domain.Stock;
import ru.dreadblade.stockmarket.accountservice.domain.StockOnAccount;
import ru.dreadblade.stockmarket.accountservice.event.OrderConfirmedIntegrationEvent;
import ru.dreadblade.stockmarket.accountservice.event.OrderCreatedIntegrationEvent;
import ru.dreadblade.stockmarket.accountservice.event.OrderRejectedIntegrationEvent;
import ru.dreadblade.stockmarket.accountservice.event.bus.EventBus;
import ru.dreadblade.stockmarket.accountservice.repository.AccountRepository;
import ru.dreadblade.stockmarket.accountservice.repository.StockOnAccountRepository;
import ru.dreadblade.stockmarket.accountservice.repository.StockRepository;

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

    @KafkaListener(groupId = "account-service-group", topics = "order-created")
    @Override
    public void handleIntegrationEvent(OrderCreatedIntegrationEvent integrationEvent) {
        log.trace("Handling integration event: {} ({}): {}\n", integrationEvent.getId().toString(),
                integrationEvent.getClass().getSimpleName(), integrationEvent.getCreatedAt().toString());

        Account account = accountRepository.findById(integrationEvent.getAccountId()).orElseThrow(IllegalStateException::new);

        Long orderStockQuantity = integrationEvent.getStockQuantity();
        BigDecimal orderPrice = integrationEvent.getPricePerStock()
                .multiply(new BigDecimal(orderStockQuantity));

        if (PURCHASE.equals(integrationEvent.getOrderType())) {
            BigDecimal reservedAccountBalance = account.getReservedBalance();

            if (account.getBalance().subtract(reservedAccountBalance).compareTo(orderPrice) >= 0) {
                account.setReservedBalance(reservedAccountBalance.add(orderPrice));

                accountRepository.save(account);

                eventBus.publish("order-confirmed", new OrderConfirmedIntegrationEvent(integrationEvent.getOrderId()));
            } else {
                eventBus.publish("order-rejected", new OrderRejectedIntegrationEvent(integrationEvent.getOrderId()));
            }
        }

        if (SALE.equals(integrationEvent.getOrderType())) {
            Stock stock = stockRepository.findById(integrationEvent.getStockId()).orElseThrow(IllegalStateException::new);

            Optional<StockOnAccount> stockOnAccountOptional = stockOnAccountRepository.findByAccountAndStock(account, stock);

            if (stockOnAccountOptional.isEmpty()) {
                eventBus.publish("order-rejected", new OrderRejectedIntegrationEvent(integrationEvent.getOrderId()));
                return;
            }

            StockOnAccount stockOnAccount = stockOnAccountOptional.get();

            Long stockQuantityAtAccount = stockOnAccount.getQuantity();
            if (stockQuantityAtAccount - stockOnAccount.getReservedQuantity() >= orderStockQuantity) {
                stockOnAccount.setQuantity(stockQuantityAtAccount - orderStockQuantity);
                stockOnAccount.setReservedQuantity(stockOnAccount.getReservedQuantity() + orderStockQuantity);

                stockOnAccountRepository.save(stockOnAccount);

                eventBus.publish("order-confirmed", new OrderConfirmedIntegrationEvent(integrationEvent.getOrderId()));
            } else {
                eventBus.publish("order-rejected", new OrderRejectedIntegrationEvent(integrationEvent.getOrderId()));
            }
        }
    }
}
