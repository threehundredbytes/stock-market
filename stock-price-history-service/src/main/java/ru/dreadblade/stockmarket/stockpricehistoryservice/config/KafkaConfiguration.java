package ru.dreadblade.stockmarket.stockpricehistoryservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
public class KafkaConfiguration {

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
        return new NewTopic("stock-created", 1, (short) 1);
    }

    @Bean
    public NewTopic stockPriceChangesTopic() {
        return new NewTopic("stock-price-changes", 1, (short) 1);
    }
}