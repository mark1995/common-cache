package com.mark.cache;

/**
 * @description: 缓存刷新线程变量，区别线程变量和生命周期变量
 * @author: GXK
 * @create: 2023-06-29 15:25
 **/

public class CacheRefreshThreadLocal {

    private final static ThreadLocal<Boolean> REFRESH_FLAG = new ThreadLocal<>();


    public static void remove() {
        REFRESH_FLAG.remove();
    }

    public static void needRefresh() {
        REFRESH_FLAG.set(true);
    }

    public static boolean isNeedRefresh() {
        Boolean refreshFlag = REFRESH_FLAG.get();
        if (refreshFlag == null) {
            return false;
        }
        return refreshFlag;
    }
}


