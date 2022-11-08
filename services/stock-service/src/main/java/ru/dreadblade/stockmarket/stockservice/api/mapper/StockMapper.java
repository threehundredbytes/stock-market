package ru.dreadblade.stockmarket.stockservice.api.mapper;

import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.stockservice.api.model.StockRequestDTO;
import ru.dreadblade.stockmarket.stockservice.api.model.StockResponseDTO;
import ru.dreadblade.stockmarket.stockservice.domain.Stock;

@Component
public class StockMapper {
    public StockResponseDTO mapEntityToResponseDTO(Stock stock) {
        return StockResponseDTO.builder()
                .id(stock.getId())
                .name(stock.getName())
                .ticker(stock.getTicker())
                .price(stock.getPrice())
                .build();
    }

    public Stock mapRequestDtoToEntity(StockRequestDTO stockRequestDTO) {
        return Stock.builder()
                .name(stockRequestDTO.name())
                .ticker(stockRequestDTO.ticker())
                .price(stockRequestDTO.price())
                .build();
    }
}