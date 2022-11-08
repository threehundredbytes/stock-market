package ru.dreadblade.stockmarket.stockpriceservice.repository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import ru.dreadblade.stockmarket.stockpriceservice.model.StockPrice;

import java.util.List;
import java.util.Set;

@Repository
public class StockRepository {
    private static final String STOCK_PRICE_KEY = "STOCK_PRICE";

    private final HashOperations<String, Long, StockPrice> hashOperations;

    public StockRepository(RedisTemplate<String, StockPrice> redisTemplate) {
        hashOperations = redisTemplate.opsForHash();
    }

    public List<StockPrice> findAll() {
        Set<Long> stockIds = hashOperations.keys(STOCK_PRICE_KEY);
        return hashOperations.multiGet(STOCK_PRICE_KEY, stockIds);
    }

    public void save(StockPrice stockPrice) {
        hashOperations.put(STOCK_PRICE_KEY, stockPrice.getStockId(), stockPrice);
    }
}
