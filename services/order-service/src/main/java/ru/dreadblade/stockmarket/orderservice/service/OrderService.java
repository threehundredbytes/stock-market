package ru.dreadblade.stockmarket.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.dreadblade.stockmarket.orderservice.api.mapper.OrderMapper;
import ru.dreadblade.stockmarket.orderservice.domain.Account;
import ru.dreadblade.stockmarket.orderservice.domain.Order;
import ru.dreadblade.stockmarket.orderservice.domain.Stock;
import ru.dreadblade.stockmarket.orderservice.event.OrderCreatedIntegrationEvent;
import ru.dreadblade.stockmarket.orderservice.event.bus.EventBus;
import ru.dreadblade.stockmarket.orderservice.api.model.OrderRequestDTO;
import ru.dreadblade.stockmarket.orderservice.api.model.OrderResponseDTO;
import ru.dreadblade.stockmarket.orderservice.repository.AccountRepository;
import ru.dreadblade.stockmarket.orderservice.repository.OrderRepository;
import ru.dreadblade.stockmarket.orderservice.repository.StockRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;
    private final AccountRepository accountRepository;
    private final OrderMapper orderMapper;
    private final EventBus eventBus;

    public List<OrderResponseDTO> findAllByAccountId(Long accountId, String userId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!userId.equals(account.getOwnerId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return orderRepository.findAllByAccount(account).stream()
                .map(orderMapper::mapEntityToResponseDTO)
                .toList();
    }

    public OrderResponseDTO placeOrder(OrderRequestDTO requestDTO, String userId) {
        Account account = accountRepository.findById(requestDTO.accountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!userId.equals(account.getOwnerId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Stock stock = stockRepository.findById(requestDTO.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Order order = Order.builder()
                .stock(stock)
                .account(account)
                .pricePerStock(requestDTO.pricePerStock())
                .initialQuantity(requestDTO.quantity())
                .currentQuantity(requestDTO.quantity())
                .orderType(requestDTO.orderType())
                .build();

        order = orderRepository.save(order);

        var orderCreatedIntegrationEvent = OrderCreatedIntegrationEvent.builder()
                .orderId(order.getId())
                .accountId(account.getId())
                .stockId(stock.getId())
                .stockTicker(stock.getTicker())
                .stockQuantity(order.getInitialQuantity())
                .pricePerStock(order.getPricePerStock())
                .orderType(order.getOrderType())
                .build();

        eventBus.publish("order-created", orderCreatedIntegrationEvent);

        return orderMapper.mapEntityToResponseDTO(order);
    }
}
