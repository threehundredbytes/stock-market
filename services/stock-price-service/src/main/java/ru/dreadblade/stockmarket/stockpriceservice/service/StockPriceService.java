package ru.dreadblade.stockmarket.stockpriceservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.dreadblade.stockmarket.stockpriceservice.event.StockPriceChangeIntegrationEvent;
import ru.dreadblade.stockmarket.stockpriceservice.event.bus.EventBus;
import ru.dreadblade.stockmarket.stockpriceservice.repository.StockRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockPriceService {
    private final StockRepository stockRepository;
    private final EventBus eventBus;

    @Scheduled(fixedRate = 2000L)
    public void changeStockPrices() {
        log.trace("Changing stock prices");

        stockRepository.findAll().stream()
                .filter(s -> RandomUtils.nextInt(0, 100) <= 42)
                .peek(stock -> {
                    BigDecimal priceFactor = new BigDecimal("0.001");
                    BigDecimal randomPriceFactor = new BigDecimal(RandomUtils.nextInt(2, 3));

                    int randomInt = RandomUtils.nextInt(0, 100);

                    if (randomInt == 99) {
                        randomPriceFactor = new BigDecimal(RandomUtils.nextInt(9, 11));
                    } else if (randomInt == 98) {
                        randomPriceFactor = new BigDecimal(RandomUtils.nextInt(7, 9));
                    } else if (randomInt == 97) {
                        randomPriceFactor = new BigDecimal(RandomUtils.nextInt(5, 7));
                    } else if (randomInt >= 95) {
                        randomPriceFactor = new BigDecimal(RandomUtils.nextInt(3, 5));
                    }

                    BigDecimal priceAbsoluteChange = stock.getPrice().multiply(priceFactor.multiply(randomPriceFactor));

                    boolean isGrowth = RandomUtils.nextInt(0, 100) <= 49;

                    BigDecimal price = stock.getPrice();

                    if (isGrowth) {
                        price = price.add(priceAbsoluteChange);
                    } else {
                        price = price.subtract(priceAbsoluteChange);
                    }

                    stock.setPrice(price.setScale(2, RoundingMode.HALF_EVEN));
                })
                .forEach(stock -> {
                    stockRepository.save(stock);

                    StockPriceChangeIntegrationEvent event = StockPriceChangeIntegrationEvent.builder()
                            .stockId(stock.getStockId())
                            .newPrice(stock.getPrice())
                            .changedAt(Instant.now())
                            .build();

                    eventBus.publish("stock-price-change", event);
                });
    }
}
