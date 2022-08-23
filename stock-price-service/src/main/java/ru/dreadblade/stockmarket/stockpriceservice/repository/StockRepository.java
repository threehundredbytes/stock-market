package ru.dreadblade.stockmarket.stockpriceservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dreadblade.stockmarket.stockpriceservice.domain.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
