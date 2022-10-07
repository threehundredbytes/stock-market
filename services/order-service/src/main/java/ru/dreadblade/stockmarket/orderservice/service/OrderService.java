package ru.dreadblade.stockmarket.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.dreadblade.stockmarket.orderservice.api.mapper.OrderMapper;
import ru.dreadblade.stockmarket.orderservice.api.model.OrderRequestDTO;
import ru.dreadblade.stockmarket.orderservice.api.model.OrderResponseDTO;
import ru.dreadblade.stockmarket.orderservice.config.KafkaTopics;
import ru.dreadblade.stockmarket.orderservice.domain.Account;
import ru.dreadblade.stockmarket.orderservice.domain.Order;
import ru.dreadblade.stockmarket.orderservice.domain.Stock;
import ru.dreadblade.stockmarket.orderservice.event.OrderCreatedIntegrationEvent;
import ru.dreadblade.stockmarket.orderservice.repository.AccountRepository;
import ru.dreadblade.stockmarket.orderservice.repository.OrderRepository;
import ru.dreadblade.stockmarket.orderservice.repository.StockRepository;
import ru.dreadblade.stockmarket.shared.event.bus.EventBus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;
    private final AccountRepository accountRepository;

    private final KafkaTopics kafkaTopics;
    private final EventBus eventBus;

    private final OrderMapper orderMapper;

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

    public List<OrderResponseDTO> findAllByStockId(Long stockId, String userId) {
        return orderRepository.findAllByStockIdAndAccountOwnerId(stockId, userId).stream()
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

        eventBus.publish(kafkaTopics.getOrderCreated(), orderCreatedIntegrationEvent);

        return orderMapper.mapEntityToResponseDTO(order);
    }
}
