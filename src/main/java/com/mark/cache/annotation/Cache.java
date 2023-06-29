package com.mark.cache.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Cache {

    /**
     * 缓存秒数
     * @return
     */
    int seconds() default 600;

    /**
     * 是否缓存null,防止缓存穿透
     * @return
     */
    boolean cacheNull() default true;


    /**
     * qps多少开始缓存
     * @return
     */
    int queryPerSeconds() default -1;
}
