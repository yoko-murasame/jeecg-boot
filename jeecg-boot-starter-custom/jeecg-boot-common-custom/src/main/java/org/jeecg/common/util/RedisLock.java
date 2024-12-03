package org.jeecg.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisLock {

    private static final Long SUCCESS = 1L;
    private long timeout = 9999; //获取锁的超时时间

    /**
     * 加锁，无阻塞
     *
     * @param
     * @param
     * @return
     */
    public static Boolean tryLock(RedisTemplate redisTemplate, String key, String value, long expireTime) {
        try {
            //SET命令返回OK ，则证明获取锁成功
            Boolean ret = redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, TimeUnit.MILLISECONDS);
            return ret;
        } catch (Exception e) {
            log.error("redis分布式锁异常", e);
            return false;
        }
    }

/*
        Long start = System.currentTimeMillis();
        for(;;){
        //SET命令返回OK ，则证明获取锁成功
        Boolean ret = redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, TimeUnit.SECONDS);
            return ret;
        //否则循环等待，在timeout时间内仍未获取到锁，则获取失败
        long end = System.currentTimeMillis() - start;
        if (end>=timeout) {
            return false;
        }
    }*/


    /**
     * 解锁
     *
     * @param
     * @param
     * @return
     */
    public static Boolean unlock(RedisTemplate redisTemplate, String key, String value) {
        try {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);
            //redis脚本执行
            //Object result = redisTemplate.execute(redisScript, Collections.singletonList(key), value))
            Object result = redisTemplate.delete(Collections.singletonList(key));
            if (SUCCESS.equals(result)) {
                return true;
            }
        } catch (Exception e) {
            log.error("redis分布式锁异常", e);
            return false;
        }
        return false;
    }

}
