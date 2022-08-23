package ru.dreadblade.stockmarket.stockservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dreadblade.stockmarket.stockservice.domain.Stock;
import ru.dreadblade.stockmarket.stockservice.dto.StockRequestDTO;
import ru.dreadblade.stockmarket.stockservice.event.StockCreatedIntegrationEvent;
import ru.dreadblade.stockmarket.stockservice.event.bus.EventBus;
import ru.dreadblade.stockmarket.stockservice.repository.StockRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final EventBus eventBus;

    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    public Stock addStock(StockRequestDTO stockRequestDTO) {
        Stock stock = stockRepository.save(Stock.builder()
                .name(stockRequestDTO.name())
                .code(stockRequestDTO.code())
                .price(stockRequestDTO.price())
                .build());

        StockCreatedIntegrationEvent stockCreatedIntegrationEvent = StockCreatedIntegrationEvent.builder()
                .stockId(stock.getId())
                .stockPrice(stock.getPrice())
                .stockCreatedAt(stock.getCreatedAt())
                .build();

        eventBus.publish("stock-created", stockCreatedIntegrationEvent);

        return stock;
    }
}
