package ru.dreadblade.stockmarket.stockpriceservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.kafka.topic")
@Getter
@Setter
@RefreshScope
public class KafkaTopics {
    private String stockCreated;
    private String stockPriceChange;
}
