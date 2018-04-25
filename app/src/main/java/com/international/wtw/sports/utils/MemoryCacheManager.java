package com.international.wtw.sports.utils;

import android.util.LruCache;

/**
 * 内存缓存管理, 采用LruCache算法
 */

public class MemoryCacheManager {

    private LruCache<String, Object> mMemoryCache;

    private static class Holder {
        static final MemoryCacheManager INSTANCE = new MemoryCacheManager();
    }

    public static MemoryCacheManager getInstance() {
        return Holder.INSTANCE;
    }

    private MemoryCacheManager() {
        int cacheSize = (int) (Runtime.getRuntime().maxMemory() / 1024) / 8;
        mMemoryCache = new LruCache<>(cacheSize);
    }

    public void put(String key, Object mObject) {
        mMemoryCache.put(key, mObject);
    }

    public Object get(String key) {
        return mMemoryCache.get(key);
    }
}
