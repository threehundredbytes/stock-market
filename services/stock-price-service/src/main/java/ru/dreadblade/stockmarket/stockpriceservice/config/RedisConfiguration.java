package ru.dreadblade.stockmarket.stockpriceservice.config;

import io.lettuce.core.ClientOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import ru.dreadblade.stockmarket.stockpriceservice.model.StockPrice;

@Configuration
public class RedisConfiguration {
    @Bean
    public ClientOptions clientOptions() {
        return ClientOptions.builder()
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                .build();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, StockPrice> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, StockPrice> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setEnableTransactionSupport(true);
        return template;
    }
}
