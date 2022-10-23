package ru.dreadblade.stockmarket.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.dreadblade.stockmarket.orderservice.domain.*;

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
                    "and o.stock = :stock " +
                    "and o.pricePerStock >= :current_price and o.pricePerStock <= :new_price " +
                    "or o.pricePerStock >= :new_price and o.pricePerStock <= :current_price " +
                    "and o.currentQuantity > 0 " +
                    "order by o.id asc"
    )
    List<Order> findConfirmedSaleOrdersByStockAndPriceBetween(@Param("stock") Stock stock,
                                                              @Param("current_price") BigDecimal currentPrice,
                                                              @Param("new_price") BigDecimal newPrice);

    @Query(
            "select o from Order as o " +
                    "where o.orderStatus = '" + OrderStatus.OrderStatusConstants.CONFIRMED_VALUE + "' " +
                    "and o.orderType = '" + OrderType.OrderTypeConstants.PURCHASE_VALUE + "' " +
                    "and o.stock = :stock " +
                    "and o.pricePerStock >= :current_price and o.pricePerStock <= :new_price " +
                    "or o.pricePerStock >= :new_price and o.pricePerStock <= :current_price " +
                    "and o.currentQuantity > 0 " +
                    "order by o.id asc")
    Optional<Order> findConfirmedPurchaseOrderByStockAndPriceBetween(@Param("stock") Stock stock,
                                                                     @Param("current_price") BigDecimal currentPrice,
                                                                     @Param("new_price") BigDecimal newPrice);

    @Query(
            "select o from Order as o " +
                    "where o.orderStatus = '" + OrderStatus.OrderStatusConstants.CONFIRMED_VALUE + "' " +
                    "and o.orderType = '" + OrderType.OrderTypeConstants.PURCHASE_VALUE + "' " +
                    "and o.stock = :stock " +
                    "and o.pricePerStock >= :current_price and o.pricePerStock <= :new_price " +
                    "or o.pricePerStock >= :new_price and o.pricePerStock <= :current_price " +
                    "and o.currentQuantity > 0 " +
                    "order by o.id asc")
    List<Order> findConfirmedPurchaseOrdersByStockAndPriceBetween(@Param("stock") Stock stock,
                                                                  @Param("current_price") BigDecimal currentPrice,
                                                                  @Param("new_price") BigDecimal newPrice);
}
