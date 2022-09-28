package ru.dreadblade.stockmarket.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.dreadblade.stockmarket.notificationservice.domain.Notification;

import java.math.BigDecimal;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUserIdAndIsActive(String userId, Boolean isActive);

    @Query("select n from Notification as n " +
            "where n.atPrice between :current_price and :new_price " +
            "and n.isActive = true and n.stock.id = :stock_id")
    List<Notification> findAllActiveNotificationsByStockIdAndPriceBetween(@Param("stock_id") Long stockId,
                                                       @Param("current_price") BigDecimal currentPrice,
                                                       @Param("new_price") BigDecimal newPrice);
}
