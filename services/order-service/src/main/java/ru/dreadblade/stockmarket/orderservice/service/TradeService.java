package ru.dreadblade.stockmarket.orderservice.service;

import ru.dreadblade.stockmarket.orderservice.domain.Stock;

import java.math.BigDecimal;

public interface TradeService {
    void processOrders(Stock stock, BigDecimal currentStockPrice, BigDecimal newStockPrice);
}
