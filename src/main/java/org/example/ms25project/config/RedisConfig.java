package org.example.ms25project.config;

import lombok.RequiredArgsConstructor;
import org.example.ms25project.dto.ShoppingCartsResponseDto;
import org.example.ms25project.entity.ShoppingCarts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private Integer port;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, ShoppingCarts> redisTemplate() {
        final RedisTemplate<String, ShoppingCarts> template = new RedisTemplate<>();
        template.setConnectionFactory(this.redisConnectionFactory());
        var stringSerializer = new StringRedisSerializer();
        var jacksonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(jacksonRedisSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(jacksonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisTemplate<String, ShoppingCartsResponseDto> responseDtoRedisTemplate() {
        final RedisTemplate<String, ShoppingCartsResponseDto> template = new RedisTemplate<>();
        template.setConnectionFactory(this.redisConnectionFactory());
        var stringSerializer = new StringRedisSerializer();
        var jacksonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(jacksonRedisSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(jacksonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
