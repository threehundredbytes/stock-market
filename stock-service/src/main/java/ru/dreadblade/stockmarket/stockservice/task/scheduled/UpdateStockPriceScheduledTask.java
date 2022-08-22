package ru.dreadblade.stockmarket.stockservice.task.scheduled;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.stockservice.repository.StockRepository;

import java.math.BigDecimal;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class UpdateStockPriceScheduledTask implements ScheduledTask {

    private final StockRepository stockRepository;

    @Override
    @Scheduled(fixedRate = 2500L, initialDelay = 1000L)
    public void run() {
        stockRepository.findLatestStock().stream()
                .peek(stock -> {
                    if (RandomUtils.nextInt(0, 100) <= 42) {
                        BigDecimal priceFactor = new BigDecimal("0.001");
                        BigDecimal randomPriceFactor = new BigDecimal(RandomUtils.nextInt(1, 3));

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

                        boolean isGrowth = RandomUtils.nextInt(0, 100) >= 42;

                        if (isGrowth) {
                            stock.setPrice(stock.getPrice().add(priceAbsoluteChange));
                        } else {
                            stock.setPrice(stock.getPrice().subtract(priceAbsoluteChange));
                        }
                    }

                    stock.setCreatedAt(Instant.now());
                })
                .forEach(stockRepository::save);
    }
}
