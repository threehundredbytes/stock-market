package ru.dreadblade.stockmarket.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.dreadblade.stockmarket.orderservice.domain.Account;
import ru.dreadblade.stockmarket.orderservice.domain.Order;
import ru.dreadblade.stockmarket.orderservice.domain.OrderStatus;
import ru.dreadblade.stockmarket.orderservice.domain.OrderType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByAccount(Account account);
    List<Order> findAllByStockIdAndAccountOwnerId(Long stockId, String userId);

    @Query(
            "select o from Order as o " +
                    "where o.orderStatus = '" + OrderStatus.OrderStatusConstants.CONFIRMED_VALUE + "' " +
                    "and o.orderType = '" + OrderType.OrderTypeConstants.SALE_VALUE + "' " +
                    "and o.stock.id = :stock_id " +
                    "and o.pricePerStock <= :price " +
                    "and o.currentQuantity > 0 " +
                    "order by o.id asc"
    )
    List<Order> findConfirmedSaleOrdersByStockIdAndPriceIsLessOrEqualThan(@Param("stock_id") Long stockId, @Param("price") BigDecimal price);

    @Query(
            "select o from Order as o " +
                    "where o.orderStatus = '" + OrderStatus.OrderStatusConstants.CONFIRMED_VALUE + "' " +
                    "and o.orderType = '" + OrderType.OrderTypeConstants.PURCHASE_VALUE + "' " +
                    "and o.stock.id = :stock_id " +
                    "and o.pricePerStock >= :price " +
                    "and o.currentQuantity > 0 " +
                    "order by o.id asc")
    Optional<Order> findConfirmedPurchaseOrderByStockIdAndPriceIsGreaterOrEqualThan(@Param("stock_id") Long stockId, @Param("price") BigDecimal price);
}
