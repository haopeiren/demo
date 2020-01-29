package hpr.utils.cache;

import java.util.function.Function;

/**
 * 缓存提供者接口
 * @author haopeiren
 * @since 2020/1/28
 */
public interface CacheProviderService
{
    /**
     * 查询缓存
     * @param key 缓存键  不能为空
     * @param <T> T
     * @return 缓存值
     */
    <T> T get(String key);

    /**
     * 查询缓存
     * @param key  缓存键 不能为空
     * @param callBack  如果没有缓存  调用callBack函数返回对象，可以为空
     * @param <T>   T
     * @return 缓存值
     */
    <T> T get(String key, Function<String, T> callBack);

    /**
     * 查询缓存
     * @param key  缓存键 不能为空
     * @param callBack  如果没有缓存  调用callBack函数返回对象，可以为空
     * @param funcParam callBack参数
     * @param <T> T
     * @param <M> M
     * @return  缓存值
     */
    <T, M> T get(String key, Function<M, T> callBack, M funcParam);

    /**
     * 查询缓存
     * @param key  缓存键 不能为空
     * @param callBack  如果没有缓存  调用callBack函数返回对象，可以为空
     * @param expireTime 过期时间
     * @param <T>   T
     * @return 缓存值
     */
    <T> T get(String key, Function<String, T> callBack, Long expireTime);

    /**
     * 查询缓存
     * @param key  缓存键 不能为空
     * @param callBack  如果没有缓存  调用callBack函数返回对象，可以为空
     * @param funcParam callBack参数
     * @param expireTime 过期时间
     * @param <T> T
     * @param <M> M
     * @return  缓存值
     */
    <T, M> T get(String key, Function<M, T> callBack, M funcParam, Long expireTime);

    /**
     * 设置缓存键值对
     * @param key 键
     * @param value 值
     * @param <T>   T
     */
    <T> void set(String key, T value);

    /**
     * 设置缓存键值对
     * @param key 键
     * @param value 值
     * @param expireTime 过期时间
     * @param <T>   T
     */
    <T> void set(String key, T value, Long expireTime);

    /**
     * 删除键
     * @param key 键
     */
    void remove(String key);

    /**
     * 是否存在缓存键
     * @param key 键
     * @return 是否存在
     */
    boolean contains(String key);
}
