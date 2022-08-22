package ru.dreadblade.stockmarket.stockservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.dreadblade.stockmarket.stockservice.domain.Stock;
import ru.dreadblade.stockmarket.stockservice.domain.key.StockCompositeKey;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, StockCompositeKey> {
    @Query(
            value = "select * from stock order by created_at desc limit (select count(distinct(code)) from stock);",
            nativeQuery = true
    )
    List<Stock> findLatestStock();
}
