package ru.dreadblade.stockmarket.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dreadblade.stockmarket.notificationservice.domain.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
