package com.mark.cache.biz;

import java.util.Date;

public interface CacheEntity {

    /**
     * 获取值
     * @return
     */
    Object getValue();

    /**
     * 是否过期
     * @return
     */
    boolean isExpired();

    /**
     * 过期时间
     * @return
     */
    Date getExpires();

    /**
     * 缓存时间
     * @return
     */
    Date cacheTime();


}
