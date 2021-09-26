package com.clairvoyant.assignment.weatherapi.confiq;

import com.clairvoyant.assignment.weatherapi.dto.WeatherDto;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
@Configuration
@AllArgsConstructor
public class ApplicationConfig<T> {
    private final RedisConnectionFactory factory;

    @Bean
    public ReactiveRedisTemplate<String, WeatherDto> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<WeatherDto> serializer = new Jackson2JsonRedisSerializer<>(WeatherDto.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, WeatherDto> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, WeatherDto> context = builder.value(serializer)
                .build();
        return new ReactiveRedisTemplate<>(factory, context);
    }
}
