package hpr.utils.cache.impl;

import com.alibaba.druid.util.StringUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import hpr.utils.cache.CacheProviderService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

/**
 * 本地缓存提供者服务  Guava Cache
 * @author haopeiren
 * @since 2020/1/28
 */
@Configuration
@ComponentScan(basePackages = "hpr")
@Qualifier("localCacheService")
public class LocalCacheServiceImpl implements CacheProviderService
{

    private static final Long cacheMinute = 1000 * 60 * 30L; //30minutes

    private static Map<String, Cache<String, Object>> cacheMap = Maps.newConcurrentMap();

    static {
        Cache<String, Object> cacheContainer = CacheBuilder.newBuilder()
                .maximumSize(10)
                .expireAfterWrite(cacheMinute, TimeUnit.MILLISECONDS)
                .recordStats()
                .build();
        cacheMap.put(String.valueOf(cacheMinute), cacheContainer);
    }

    /**
     * 查询缓存
     *
     * @param key 缓存键  不能为空
     * @return 缓存值
     */
    @Override
    public <T> T get(String key)
    {
        return get(key, null, null, cacheMinute);
    }

    /**
     * 查询缓存
     *
     * @param key      缓存键 不能为空
     * @param callBack 如果没有缓存  调用callBack函数返回对象，可以为空
     * @return 缓存值
     */
    @Override
    public <T> T get(String key, Function<String, T> callBack)
    {
        return get(key, callBack, key, cacheMinute);
    }

    /**
     * 查询缓存
     *
     * @param key       缓存键 不能为空
     * @param callBack  如果没有缓存  调用callBack函数返回对象，可以为空
     * @param funcParam callBack参数
     * @return 缓存值
     */
    @Override
    public <T, M> T get(String key, Function<M, T> callBack, M funcParam)
    {
        return get(key, callBack, funcParam, cacheMinute);
    }

    /**
     * 查询缓存
     *
     * @param key        缓存键 不能为空
     * @param callBack   如果没有缓存  调用callBack函数返回对象，可以为空
     * @param expireTime 过期时间
     * @return 缓存值
     */
    @Override
    public <T> T get(String key, Function<String, T> callBack, Long expireTime)
    {
        return get(key, callBack, key, expireTime);
    }

    /**
     * 查询缓存
     *
     * @param key        缓存键 不能为空
     * @param callBack   如果没有缓存  调用callBack函数返回对象，可以为空
     * @param funcParam  callBack参数
     * @param expireTime 过期时间
     * @return 缓存值
     */
    @Override
    public <T, M> T get(String key, Function<M, T> callBack, M funcParam, Long expireTime)
    {
        T result = null;
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        expireTime = getExpireTime(expireTime);
        Cache<String, Object> cacheContainer = getContainer(expireTime);

        try
        {
            if (callBack == null)
            {
                result = (T) cacheContainer.getIfPresent(key);
            }
            else
            {
                final Long cacheTime = expireTime;
                result = (T) cacheContainer.get(key, () ->{
                    T resObj = callBack.apply(funcParam);
                    return resObj;
                });
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置缓存键值对
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public <T> void set(String key, T value)
    {
        set(key, value, cacheMinute);
    }

    /**
     * 设置缓存键值对
     *
     * @param key        键
     * @param value      值
     * @param expireTime 过期时间
     */
    @Override
    public <T> void set(String key, T value, Long expireTime)
    {
        if (StringUtils.isEmpty(key))
        {
            return;
        }
        if (value == null)
        {
            return;
        }
        expireTime = getExpireTime(expireTime);
        Cache<String, Object> cacheContainer = getContainer(expireTime);
        cacheContainer.put(key, value);
    }

    /**
     * 删除键
     *
     * @param key 键
     */
    @Override
    public void remove(String key)
    {
        if (StringUtils.isEmpty(key))
        {
            return;
        }
        Long expireTime = getExpireTime(cacheMinute);
        Cache<String, Object> cacheContainer = getContainer(expireTime);
        cacheContainer.invalidate(key);
    }

    /**
     * 是否存在缓存键
     *
     * @param key 键
     * @return 是否存在
     */
    @Override
    public boolean contains(String key)
    {
        if (StringUtils.isEmpty(key))
        {
            return false;
        }
        Object obj = get(key);
        if (obj != null)
        {
            return true;
        }
        return false;
    }

    private static Lock lock = new ReentrantLock();

    private Cache<String, Object> getContainer(Long expireTime)
    {
        Cache<String, Object> cacheContainer;
        if (expireTime == null)
        {
            return null;
        }
        String mapKey = String.valueOf(expireTime);
        if (cacheMap.containsKey(mapKey))
        {
            cacheContainer = cacheMap.get(mapKey);
            return cacheContainer;
        }
        try
        {
            lock.lock();
            cacheContainer = CacheBuilder.newBuilder()
                    .maximumSize(10)
                    .expireAfterWrite(cacheMinute, TimeUnit.MILLISECONDS)
                    .recordStats()
                    .build();
            cacheMap.put(mapKey, cacheContainer);
        }
        finally {
            lock.unlock();
        }
        return cacheContainer;


    }

    /**
     * 获取过期时间，单位：毫秒
     * @param expireTime 过期时间
     * @return  如果传入的时间小于 cacheMinute/10  则设置时间为默认过期时间
     */
    public Long getExpireTime(Long expireTime)
    {
        Long result = expireTime;
        if (expireTime == null || expireTime < cacheMinute / 10)
        {
            result = cacheMinute;
        }
        return result;
    }
}
