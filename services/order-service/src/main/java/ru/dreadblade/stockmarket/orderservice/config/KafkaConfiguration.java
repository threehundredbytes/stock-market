package ru.dreadblade.stockmarket.orderservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.dreadblade.stockmarket.orderservice.event.IntegrationEvent;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {
    private final KafkaProperties kafkaProperties;

    public ProducerFactory<String, IntegrationEvent> producerFactory() {
        Map<String, Object> configurationProperties = new HashMap<>(kafkaProperties.buildProducerProperties());

        configurationProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configurationProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configurationProperties);
    }

    @Bean
    public KafkaTemplate<String, IntegrationEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

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
    public NewTopic orderCreatedTopic() {
        return new NewTopic("order-created", 1, (short) 1);
    }

    @Bean
    public NewTopic orderConfirmedTopic() {
        return new NewTopic("order-confirmed", 1, (short) 1);
    }

    @Bean
    public NewTopic orderRejectedTopic() {
        return new NewTopic("order-rejected", 1, (short) 1);
    }

    @Bean
    public NewTopic stockCreatedTopic() {
        return new NewTopic("stock-created", 1, (short) 1);
    }

    @Bean
    public NewTopic stockPriceChangesTopic() {
        return new NewTopic("stock-price-changes", 1, (short) 1);
    }

    @Bean
    public NewTopic accountCreatedTopic() {
        return new NewTopic("account-created", 1, (short) 1);
    }
}
