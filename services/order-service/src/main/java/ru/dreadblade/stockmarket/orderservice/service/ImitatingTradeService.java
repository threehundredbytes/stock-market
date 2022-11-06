package ru.dreadblade.stockmarket.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.dreadblade.stockmarket.orderservice.domain.Order;
import ru.dreadblade.stockmarket.orderservice.domain.Stock;
import ru.dreadblade.stockmarket.orderservice.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@Primary
@Profile("simulate-trading")
@RequiredArgsConstructor
public class ImitatingTradeService implements TradeService {
    private final DefaultTradeService defaultTradeService;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Override
    public void processOrders(Stock stock, BigDecimal currentStockPrice, BigDecimal newStockPrice) {
        defaultTradeService.processOrders(stock, currentStockPrice, newStockPrice);

        List<Order> confirmedSaleOrdersToClose = orderRepository
                .findConfirmedSaleOrdersByStockAndPriceBetween(stock, currentStockPrice, newStockPrice);

        if (!confirmedSaleOrdersToClose.isEmpty()) {
            log.info("Closing {} sale orders automatically", confirmedSaleOrdersToClose.size());

            confirmedSaleOrdersToClose.forEach(order -> {
                order.setCurrentQuantity(0L);
                orderService.closeOrder(order, Instant.now());
                orderRepository.save(order);
            });
        }

        List<Order> confirmedPurchaseOrdersToClose = orderRepository
                .findConfirmedPurchaseOrdersByStockAndPriceBetween(stock, currentStockPrice, newStockPrice);

        if (!confirmedPurchaseOrdersToClose.isEmpty()) {
            log.info("Closing {} purchase orders automatically", confirmedPurchaseOrdersToClose.size());

            confirmedPurchaseOrdersToClose.forEach(order -> {
                order.setCurrentQuantity(order.getInitialQuantity());
                orderService.closeOrder(order, Instant.now());
                orderRepository.save(order);
            });
        }
    }
}
