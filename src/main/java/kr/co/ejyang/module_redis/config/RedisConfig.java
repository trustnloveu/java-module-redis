package kr.co.ejyang.module_redis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Getter
@Setter
@Configuration
public class RedisConfig {

    private final String ip;
    private final int port;
    private final long ttl;

    RedisConfig(
        @Value("${redis.ip}") String ip,
        @Value("${redis.port}") int port,
        @Value("${redis.ttl}") long ttl
    ) {
        super();
        this.ip = ip;
        this.port = port;
        this.ttl = ttl;
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        template.setEnableDefaultSerializer(false);
        template.setEnableTransactionSupport(true);
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(ip, port);
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(1000);
        jedisPoolConfig.setMinIdle(1000);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setMaxTotal(1000); // 커넥션풀 최대 생성값 설정 ( default = 8)
        jedisPoolConfig.setBlockWhenExhausted(true); // 커넥션풀이 가득 찼을 경우 > 준비된 연결 도착 대기
        jedisPoolConfig.setBlockWhenExhausted(false); // 커넥션풀이 가득 찼을 경우 > 새로운 연결을 기다리지 않고 NoSuchElementException 예외 발생
        return jedisPoolConfig;
    }
}