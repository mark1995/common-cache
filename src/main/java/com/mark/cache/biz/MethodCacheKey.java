package com.mark.cache.biz;

import org.aspectj.lang.reflect.MethodSignature;

public interface MethodCacheKey {

    /**
     * 根据方法生成唯一的缓存key
     * @param methodSignature
     * @return
     */
    String genMethodKey(MethodSignature methodSignature);
}
