package ru.dreadblade.stockmarket.accountservice.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.accountservice.domain.Account;
import ru.dreadblade.stockmarket.accountservice.domain.Stock;
import ru.dreadblade.stockmarket.accountservice.domain.StockOnAccount;
import ru.dreadblade.stockmarket.accountservice.event.OrderClosedIntegrationEvent;
import ru.dreadblade.stockmarket.accountservice.repository.AccountRepository;
import ru.dreadblade.stockmarket.accountservice.repository.StockOnAccountRepository;
import ru.dreadblade.stockmarket.accountservice.repository.StockRepository;

import java.math.BigDecimal;

import static ru.dreadblade.stockmarket.accountservice.event.model.OrderType.PURCHASE;
import static ru.dreadblade.stockmarket.accountservice.event.model.OrderType.SALE;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderClosedIntegrationEventHandler implements IntegrationEventHandler<OrderClosedIntegrationEvent> {
    private final AccountRepository accountRepository;
    private final StockOnAccountRepository stockOnAccountRepository;
    private final StockRepository stockRepository;

    @KafkaListener(groupId = "account-service-group", topics = "order-closed")
    @Override
    public void handleIntegrationEvent(OrderClosedIntegrationEvent integrationEvent) {
        log.trace("Handling integration event: {} ({}): {}", integrationEvent.getId().toString(),
                integrationEvent.getClass().getSimpleName(), integrationEvent.getCreatedAt().toString());

        Account account = accountRepository.findById(integrationEvent.getAccountId()).orElseThrow(IllegalStateException::new);
        Stock stock = stockRepository.findById(integrationEvent.getStockId()).orElseThrow(IllegalStateException::new);

        var stockOnAccount = stockOnAccountRepository.findByAccountAndStock(account, stock).orElse(StockOnAccount.builder()
                .account(account)
                .stock(stock)
                .build());

        Long quantity = integrationEvent.getQuantity();
        BigDecimal accountBalance = account.getBalance();
        BigDecimal orderPrice = integrationEvent.getPricePerStock().multiply(new BigDecimal(quantity));

        if (PURCHASE.equals(integrationEvent.getOrderType())) {
            account.setBalance(accountBalance.subtract(orderPrice));
            account.setReservedBalance(account.getReservedBalance().subtract(orderPrice));
            accountRepository.save(account);

            stockOnAccount.setQuantity(stockOnAccount.getQuantity() + quantity);
            stockOnAccountRepository.save(stockOnAccount);
        }

        if (SALE.equals(integrationEvent.getOrderType())) {
            account.setBalance(accountBalance.add(orderPrice));
            accountRepository.save(account);

            stockOnAccount.setQuantity(stockOnAccount.getQuantity() - quantity);
            stockOnAccount.setReservedQuantity(stockOnAccount.getReservedQuantity() - quantity);
            stockOnAccountRepository.save(stockOnAccount);
        }
    }
}
