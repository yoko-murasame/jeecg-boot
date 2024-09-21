package org.jeecg.common.modules.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.Collections;
import javax.annotation.Resource;

import org.jeecg.common.constant.CacheConstant;
import org.jeecg.common.constant.GlobalConstants;
import org.jeecg.common.modules.redis.prop.JeecgRedisProperties;
import org.jeecg.common.modules.redis.receiver.RedisReceiver;
import org.jeecg.common.modules.redis.writer.JeecgRedisCacheWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableConfigurationProperties(JeecgRedisProperties.class)
@ConditionalOnProperty(value = "spring.redis.enabled", havingValue = "true", matchIfMissing = true)
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
    private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);
    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;

    @Bean("starterRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        log.info(" --- redis config init --- ");
        // 设置序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = jacksonSerializer();
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = jacksonSerializer();
        return RedisCacheManager.builder(new JeecgRedisCacheWriter(factory, Duration.ofMillis(50))).cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(6)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))).withInitialCacheConfigurations(Collections.singletonMap(CacheConstant.SYS_DICT_TABLE_CACHE, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)).disableCachingNullValues().serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer)))).withInitialCacheConfigurations(Collections.singletonMap(CacheConstant.TEST_DEMO_CACHE, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)).disableCachingNullValues())).withInitialCacheConfigurations(Collections.singletonMap(CacheConstant.PLUGIN_MALL_RANKING, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(24)).disableCachingNullValues())).withInitialCacheConfigurations(Collections.singletonMap(CacheConstant.PLUGIN_MALL_PAGE_LIST, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(24)).disableCachingNullValues())).transactionAware().build();
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory, RedisReceiver redisReceiver, MessageListenerAdapter commonListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(commonListenerAdapter, new ChannelTopic(GlobalConstants.REDIS_TOPIC_NAME));
        return container;
    }

    @Bean
    MessageListenerAdapter commonListenerAdapter(RedisReceiver redisReceiver) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(redisReceiver, "onMessage");
        messageListenerAdapter.setSerializer(jacksonSerializer());
        return messageListenerAdapter;
    }

    private Jackson2JsonRedisSerializer<Object> jacksonSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }

}
