package ru.dreadblade.stockmarket.stockpricehistoryservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.dreadblade.stockmarket.stockpricehistoryservice.domain.Stock;

public interface StockRepository extends MongoRepository<Stock, Long> {
}
