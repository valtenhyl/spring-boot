package com.valten.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 本地缓存
 *
 * @author huangyuanli
 * @className GuavaCacheUtil
 * @package com.valten.utils
 * @date 2020/7/12 12:34
 **/
public class GuavaCacheUtil {

    private static final Cache<String, String> LOCAL_CACHE = CacheBuilder.newBuilder()
            .maximumSize(40000000)// 最大缓存数据量
            .initialCapacity(10000000)// 初始容量
            .expireAfterWrite(10, TimeUnit.MINUTES)// 过期清除
            .build();

    /**
     * 从缓存读取数据
     *
     * @author huangyuanli
     * @date 2020/7/12 12:29
     */
    public static String get(String key) {
        return LOCAL_CACHE.getIfPresent(key);
    }

    /**
     * 添加获取
     *
     * @author huangyuanli
     * @date 2020/7/12 12:28
     */
    public static void put(String key, String value) {
        LOCAL_CACHE.put(key, value);
    }

}

