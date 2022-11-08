package ru.dreadblade.stockmarket.stockpricehistoryservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {
    private final KafkaTopics kafkaTopics;

    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Bean
    public StringJsonMessageConverter stringJsonMessageConverter() {
        return new StringJsonMessageConverter(objectMapper());
    }

    @Bean
    public NewTopic stockCreatedTopic() {
        return new NewTopic(kafkaTopics.getStockCreated(), 1, (short) 1);
    }

    @Bean
    public NewTopic stockPriceChangeTopic() {
        return new NewTopic(kafkaTopics.getStockPriceChange(), 1, (short) 1);
    }
}