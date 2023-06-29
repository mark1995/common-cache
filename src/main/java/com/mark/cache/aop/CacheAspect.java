package com.mark.cache.aop;

import com.mark.cache.CacheRefreshThreadLocal;
import com.mark.cache.Null;
import com.mark.cache.annotation.Cache;
import com.mark.cache.biz.CacheEntity;
import com.mark.cache.biz.Caches;
import com.mark.cache.biz.MethodCacheKey;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @description:
 * @author: GXK
 * @create: 2023-06-29 12:11
 **/


@Aspect
public class CacheAspect {

    private final static Logger logger = LoggerFactory.getLogger(CacheAspect.class);


    private Caches caches;

    private MethodCacheKey methodCacheKey;

    @Pointcut("@annotation(com.mark.cache.annotation.Cache)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object cache(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Cache annotation = AnnotationUtils.findAnnotation(methodSignature.getMethod(), Cache.class);
        String cacheKey = methodCacheKey.genMethodKey(methodSignature);
        CacheEntity cacheEntity = null;

        if (CacheRefreshThreadLocal.isNeedRefresh()) {
            CacheRefreshThreadLocal.remove();
            cacheEntity = null;
        } else {
            try {
                cacheEntity = caches.get(cacheKey);
            } catch (Exception e) {
                logger.error("cache aop get result failure [cache={}]", cacheKey);
            }
        }

        Object retVal = null;
        if (cacheEntity == null || cacheEntity.isExpired()) {
            retVal = joinPoint.proceed();
            if (retVal == null && annotation.cacheNull()) {
                caches.put(cacheKey, Null.NULL, annotation.seconds());
                retVal = Null.NULL;
            }
            if (retVal != null) {
                caches.put(cacheKey, retVal, annotation.seconds());
            }
        } else {
            retVal = cacheEntity.getValue();
        }


       if (retVal instanceof Null) {
           return null;
       }
       return retVal;
    }

}


