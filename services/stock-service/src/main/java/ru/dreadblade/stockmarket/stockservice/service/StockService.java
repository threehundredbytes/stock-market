package ru.dreadblade.stockmarket.stockservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.dreadblade.stockmarket.shared.event.bus.EventBus;
import ru.dreadblade.stockmarket.stockservice.api.mapper.StockMapper;
import ru.dreadblade.stockmarket.stockservice.api.model.StockRequestDTO;
import ru.dreadblade.stockmarket.stockservice.api.model.StockResponseDTO;
import ru.dreadblade.stockmarket.stockservice.domain.Stock;
import ru.dreadblade.stockmarket.stockservice.event.StockCreatedIntegrationEvent;
import ru.dreadblade.stockmarket.stockservice.repository.StockRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;
    private final EventBus eventBus;

    public List<StockResponseDTO> findAll() {
        return stockRepository.findAll().stream()
                .map(stockMapper::mapEntityToResponseDTO)
                .toList();
    }

    public StockResponseDTO findById(Long stockId) {
        return stockRepository.findById(stockId).map(stockMapper::mapEntityToResponseDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public StockResponseDTO addStock(StockRequestDTO stockRequestDTO) {
        Stock stock = stockRepository.save(stockMapper.mapRequestDtoToEntity(stockRequestDTO));

        StockCreatedIntegrationEvent stockCreatedIntegrationEvent = StockCreatedIntegrationEvent.builder()
                .stockId(stock.getId())
                .stockTicker(stock.getTicker())
                .stockPrice(stock.getPrice())
                .stockCreatedAt(stock.getCreatedAt())
                .build();

        eventBus.publish("stock-created", stockCreatedIntegrationEvent);

        return stockMapper.mapEntityToResponseDTO(stock);
    }
}
