package ru.dreadblade.stockmarket.stockpricehistoryservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.dreadblade.stockmarket.stockpricehistoryservice.api.mapper.StockPriceHistoryMapper;
import ru.dreadblade.stockmarket.stockpricehistoryservice.api.model.StockPriceHistoryResponseDTO;
import ru.dreadblade.stockmarket.stockpricehistoryservice.domain.StockPriceHistory;
import ru.dreadblade.stockmarket.stockpricehistoryservice.domain.key.StockPriceHistoryCompositeKey;
import ru.dreadblade.stockmarket.stockpricehistoryservice.repository.StockPriceHistoryRepository;
import ru.dreadblade.stockmarket.stockpricehistoryservice.repository.StockRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockPriceHistoryService {
    private final StockRepository stockRepository;
    private final StockPriceHistoryRepository stockHistoryRepository;
    private final StockPriceHistoryMapper stockPriceHistoryMapper;

    public List<StockPriceHistoryResponseDTO> findAllByStockId(Long stockId) {
        return stockHistoryRepository.findAllByCompositeKeyId(stockId)
                .stream()
                .map(stockPriceHistoryMapper::mapEntityToResponseDTO)
                .toList();
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void saveStockPriceInHistory() {
        log.trace("Saving stock data in history");

        Instant priceAt = Instant.now().truncatedTo(ChronoUnit.MINUTES);

        stockRepository.findAll().forEach(stock -> {
            stockHistoryRepository.save(StockPriceHistory.builder()
                    .compositeKey(StockPriceHistoryCompositeKey.builder()
                            .id(stock.getId())
                            .priceAt(priceAt)
                            .build())
                    .price(stock.getPrice())
                    .build());
        });
    }
}
