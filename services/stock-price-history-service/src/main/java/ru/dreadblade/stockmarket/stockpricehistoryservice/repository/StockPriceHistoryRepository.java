package ru.dreadblade.stockmarket.stockpricehistoryservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.dreadblade.stockmarket.stockpricehistoryservice.model.StockPriceHistory;
import ru.dreadblade.stockmarket.stockpricehistoryservice.model.key.StockPriceHistoryCompositeKey;

import java.util.List;

public interface StockPriceHistoryRepository extends MongoRepository<StockPriceHistory, StockPriceHistoryCompositeKey> {
    List<StockPriceHistory> findAllByCompositeKeyId(Long id);
}
