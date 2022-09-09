package ru.dreadblade.stockmarket.stockpricehistoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StockPriceHistoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockPriceHistoryServiceApplication.class, args);
	}

}
