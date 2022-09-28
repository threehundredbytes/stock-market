package ru.dreadblade.stockmarket.stockpricehistoryservice.api.mapper;

import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.stockpricehistoryservice.api.model.StockPriceHistoryResponseDTO;
import ru.dreadblade.stockmarket.stockpricehistoryservice.domain.StockPriceHistory;

@Component
public class StockPriceHistoryMapper {
    public StockPriceHistoryResponseDTO mapEntityToResponseDTO(StockPriceHistory stockPriceHistory) {
        return new StockPriceHistoryResponseDTO(stockPriceHistory.getPrice(), stockPriceHistory.getCompositeKey().getPriceAt());
    }
}
