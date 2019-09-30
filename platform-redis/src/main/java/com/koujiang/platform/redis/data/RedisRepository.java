package com.koujiang.platform.redis.data;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <br>(c) Copyright koujiang901123@sina.com
 * <br>@description	:redis操作工具类
 * <br>@file_name	:RedisRepository.java
 * <br>@system_name	:com.unknown.platform.data.RedisRepository
 * <br>@author		:koujiang
 * <br>@create_time	:2019/8/29 3:53
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 */
public class RedisRepository<K,V> {

    private static RedisRepository instance;

    private RedisTemplate<K,V> redisTemplate;

    private RedisRepository(RedisTemplate<K,V> redisTemplate) {
        this.redisTemplate = redisTemplate;
        instance = this;
    }

    public static <K,V> RedisRepository<K,V> getInstance(RedisTemplate<K,V> redisTemplate) {
        if (redisTemplate == null) {
            throw new RuntimeException("RedisUtil基于RedisTemplate，RedisTemplate不能为空.");
        }
        if (instance == null) {
            synchronized (RedisRepository.class) {
                if (instance == null) {
                    return new RedisRepository<>(redisTemplate);
                }
            }
        }
        return instance;
    }

    /**
     * 通过hash key获取value list
     *
     * @param key
     * @return
     */
    public List<Object> getHashToList(K key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 添加字符串到hash中
     *
     * @param hashKey
     * @param key
     * @param value
     */
    public void putStrForHash(K hashKey, String key, String value) {
        redisTemplate.opsForHash().put(hashKey, key, value);
    }

    /**
     * k-v并设置过期时间，单位毫秒
     *
     * @param key   key
     * @param value val
     * @param time  过期时间
     */
    public void putStrForValueExpire(K key, V value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
    }

    /**
     * 通过hash key和key删除一条记录
     *
     * @param hashKey
     * @param key
     */
    public void deleteStrForHashByKey(K hashKey, String key) {
        redisTemplate.opsForHash().delete(hashKey, key);
    }

    /**
     * 判断hash中的某个key是否存在
     *
     * @param hashKey
     * @param key
     * @return
     */
    public boolean hasHashKeyByKey(K hashKey, String key) {
        return redisTemplate.opsForHash().hasKey(hashKey, key);
    }

    /**
     * 通过key获取序列化后的对象
     *
     * @param key
     * @return
     */
    public V getObj(K key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通类型key是否存在
     *
     * @return 存在或不存在
     */
    public Boolean hasValueKey(K key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * @Author: yxc
     * @Description: 通过key删除值
     * @Date: 2019/1/2 15:11
     * @Param: [key]
     * @return: boolean
     */
    public Boolean deleteValueByKey(K key) {
        return redisTemplate.delete(key);
    }

    /**
     * @Author: yxc
     * @Description: 通过hash key 和 key 获取单个值
     * @Date: 2019/1/2 15:19
     * @Param: [hk, key]
     * @return: java.lang.String
     */
    public String getHashValByKey(K hk, String key) {
        Object o = redisTemplate.opsForHash().get(hk, key);
        if (o == null)
            return null;
        return o + "";
    }

    /**
     * @Author: yxc
     * @Description: 添加值到list
     * @Date: 2019/1/14 14:51
     * @Param: [key, value]
     * @return: long
     */
    public Long addValueToListLeft(K key, V value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * @Author: yxc
     * @Description: 添加值到list
     * @Date: 2019/1/14 14:51
     * @Param: [key, value]
     * @return: long
     */
    public Long addValueToListRight(K key, V value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * @Author: yxc
     * @Description: 通过key获取list
     * @Date: 2019/1/14 15:10
     * @Param: [key]
     * @return: java.util.List<java.lang.String>
     */
    public V findListLeft(K key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * @Author: yxc
     * @Description: 通过key获取list
     * @Date: 2019/1/14 15:10
     * @Param: [key]
     * @return: java.util.List<java.lang.String>
     */
    public V findListRight(K key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * @Author: yxc
     * @Description: 获取指定list长度
     * @Date: 2019/1/14 14:52
     * @Param: [key]
     * @return: long
     */
    public Long getListSize(K key) {
        return redisTemplate.opsForList().size(key);
    }

    public V getValByKey(K key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Long getExpire(K key) {
        return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
    }
}
