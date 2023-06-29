package com.mark.cache.biz;

import java.util.Date;

public interface Caches {


    /**
     * 获取缓存
     * @param cacheKey
     * @return
     */
    CacheEntity get(String cacheKey);

    /**
     * 是否存在
     * @param cacheKey
     * @return
     */
    boolean contains(String cacheKey);

    /**
     * 插入缓存
     * @param key
     * @param value
     * @param ttl 存活时间 秒
     */
    default void put(Object key, Object value, int ttl) {
        Date date = new Date(System.currentTimeMillis() + ttl * 1000);
        put(key, value, date);
    }

    void put(Object key, Object value, Date cacheAt);
}
