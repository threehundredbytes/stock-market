package ru.dreadblade.stockmarket.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dreadblade.stockmarket.orderservice.domain.Order;
import ru.dreadblade.stockmarket.orderservice.domain.Stock;
import ru.dreadblade.stockmarket.orderservice.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultTradeService implements TradeService {
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Override
    public void processOrders(Stock stock, BigDecimal currentStockPrice, BigDecimal newStockPrice) {
        List<Order> confirmedSaleOrders = orderRepository.findConfirmedSaleOrdersByStockAndPriceBetween(stock, currentStockPrice, newStockPrice);

        if (!confirmedSaleOrders.isEmpty()) {
            confirmedSaleOrders.forEach(orderForSale -> {
                while (orderForSale.getCurrentQuantity() > 0) {
                    List<Order> ordersForPurchase = orderRepository.findConfirmedPurchaseOrdersByStockAndPriceBetween(stock, currentStockPrice, newStockPrice);

                    if (ordersForPurchase.isEmpty()) {
                        break;
                    }

                    ordersForPurchase.forEach(orderForPurchase -> {
                        while (orderForPurchase.getCurrentQuantity() > 0) {
                            long quantity = Math.min(orderForSale.getCurrentQuantity(), orderForPurchase.getCurrentQuantity());

                            Instant now = Instant.now();

                            orderForPurchase.setCurrentQuantity(orderForPurchase.getCurrentQuantity() - quantity);
                            orderForSale.setCurrentQuantity(orderForSale.getCurrentQuantity() - quantity);

                            if (orderForPurchase.getCurrentQuantity() == 0) {
                                orderService.closeOrder(orderForPurchase, now);
                            }

                            if (orderForSale.getCurrentQuantity() == 0) {
                                orderService.closeOrder(orderForSale, now);
                            }

                            orderRepository.save(orderForPurchase);
                            orderRepository.save(orderForSale);
                        }
                    });
                }
            });
        }
    }


}
