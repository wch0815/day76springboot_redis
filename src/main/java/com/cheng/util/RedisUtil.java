package com.cheng.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void SetRedisTemplate(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    /**
     * 指定缓存的实效时间
     * @param key redis中的键
     * @param time 时间（秒）
     * @return 该key对应的值是否失效
     */
    public boolean expire(String key, long time){
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key拿到该key的失效时间
     * @param key
     * @return 失效失效（秒）
     */
    public long getExpire(String key){
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否在Redis中
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存中所有的keys值
     * @param keys
     */
    public void delete(String ...keys){
        if (keys != null&& keys.length>0){
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            }else {
                redisTemplate.delete(CollectionUtils.arrayToList(keys));
            }
        }
    }


//！------------------------------------String start---------------------------------------

    /**
     * 获取String类型的key对应的值
     * @param key
     * @return
     */
    public Object get(String key){
        return key == null ? null:redisTemplate.opsForValue().get(key);
    }

    /**
     * 存入String类型的数据
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value){
        try{
            redisTemplate.opsForValue().set(key, value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在redis服务器中放置String类型的值，并设置失效时间
     * @param key String类型的key
     * @param value 值
     * @param time 失效时间(秒)
     * @return
     */
    public boolean set(String key, Object value, long time){
        try{
            if (time > 0){
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }else {
                set(key, value);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
//!--------------------------------------------String end--------------------------------------------


//!--------------------------------------------hash start--------------------------------------------

    /**
     * hash类型数据的存储
     * @param key hash类型值的key
     * @param map 键值对
     * @return
     */
    public boolean hmset(String key, Map<String, Object> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置hash中指定key下的field的值为value
     * @param key hash的key键
     * @param field hash中的field域
     * @param value 给hash中的field设置的值
     * @return true设置成功，否则false
     */
    public boolean hset(String key, String field, Object value){
        try {
            redisTemplate.opsForHash().put(key, field, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置hash中指定key下的field的值为value,并设置失效时间
     * @param time 失效时间
     * @param key hash的key键
     * @param field hash中的field域
     * @param value 给hash中的field设置的值
     * @return true设置成功，否则false
     */
    public boolean hset(String key, String field, Object value, long time){
        try {
            redisTemplate.opsForHash().put(key, field, value);
            if (time > 0){
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * hash类型数据的存储
     * @param key hash类型值的key
     * @param map 键值对
     * @param time 失效时间
     * @return true代表设置成功，否则false
     */
    public boolean hmset(String key, Map<String, Object> map, long time){
        try{
            redisTemplate.opsForHash().putAll(key, map);

            if(time > 0){
                expire(key, time);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * hash类型数据的获取
     * @param key hash类型数据的键
     * @param field hash类型的field
     * @return 在该key所对应的hash中field的值
     */
    public Object hget(String key, String field){
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取hash类型数据的key对应的整个map对象
     * @param key hash中的key
     * @return 该hash key对应的hash对象
     */
    public Map<Object, Object> hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }
//!--------------------------------------------List start--------------------------------------------


    public boolean lpush(String key, Object value){
        try {
            redisTemplate.opsForList().leftPush(key, value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean lpush(String key, Object value, long time){
        try {
            redisTemplate.opsForList().leftPush(key, value);
            if(time > 0){
                expire(key, time);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Object> lrange(String key, long start, long end){
        return redisTemplate.opsForList().range(key,start,end);
    }

}
