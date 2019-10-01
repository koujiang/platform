package com.unknown.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * <br>(c) Copyright koujiang901123@sima.com
 * <br>@description	:
 *  SpringBoot自动帮我们在容器中生成了一个RedisTemplate和一个StringRedisTemplate。
 *  但是，这个RedisTemplate的泛型是<Object,Object>，写代码不方便，需要写好多类型转换的代码；
 *  我们需要一个泛型为<String,Object>形式的RedisTemplate。
 *  并且，这个RedisTemplate没有设置数据存在Redis时，key及value的序列化方式。
 *  看到这个@ConditionalOnMissingBean注解后，就知道如果Spring容器中有了RedisTemplate对象了，
 *  这个自动配置的RedisTemplate不会实例化。因此我们可以直接自己写个配置类，配置RedisTemplate
 * <br>@file_name	:null.java
 * <br>@system_name	:platform
 * <br>@author		:Administrator
 * <br>@create_time	:2019/2/19 14:31
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@See org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
 * <br>@varsion		:v1.0.0
 */
@Configuration
public class RedisConfig {

    /*
    如果不注明的话系统会默认采用RedisAutoConfiguration 进行构建类，如果需要进行调整就需要在此申明RedisTemplate
    org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
    */

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        redisTemplate.setEnableTransactionSupport(true);

        /*Jackson2JsonRedisSerializer valueRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        //序列化的时候序列对象的所有属性
        om.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 如果json中有新增的字段并且是实体类类中不存在的，不报错
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //如果是空对象的时候,不抛异常
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //取消时间的转化格式,默认是时间戳,可以取消,同时需要设置要表现的时间格式
        //om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 解决jackson2无法反序列化LocalDateTime的问题
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        valueRedisSerializer.setObjectMapper(om);*/

        // key采用String的序列化方式
        redisTemplate.setKeySerializer(RedisSerializer.string());
        // hash的key也采用String的序列化方式
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        // value序列化方式采用jackson
        /*redisTemplate.setValueSerializer(RedisSerializer.string());
        // hash的value序列化方式采用jackson
        redisTemplate.setHashValueSerializer(RedisSerializer.string());
        redisTemplate.setDefaultSerializer(RedisSerializer.string());*/
        redisTemplate.afterPropertiesSet();
        //redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisTemplate redisTemplate) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisTemplate.getConnectionFactory());
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()));
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }
}
