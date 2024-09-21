package org.jeecg.common.modules.redis.writer;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.data.redis.cache.CacheStatistics;
import org.springframework.data.redis.cache.CacheStatisticsCollector;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class JeecgRedisCacheWriter implements RedisCacheWriter {
    private static final Logger log = LoggerFactory.getLogger(JeecgRedisCacheWriter.class);
    private final RedisConnectionFactory connectionFactory;
    private final Duration sleepTime;
    private final CacheStatisticsCollector statistics;

    public JeecgRedisCacheWriter(RedisConnectionFactory connectionFactory) {
        this(connectionFactory, Duration.ZERO);
    }

    public JeecgRedisCacheWriter(RedisConnectionFactory connectionFactory, Duration sleepTime) {
        this.statistics = CacheStatisticsCollector.create();
        Assert.notNull(connectionFactory, "ConnectionFactory must not be null!");
        Assert.notNull(sleepTime, "SleepTime must not be null!");
        this.connectionFactory = connectionFactory;
        this.sleepTime = sleepTime;
    }

    public void put(String name, byte[] key, byte[] value, @Nullable Duration ttl) {
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(key, "Key must not be null!");
        Assert.notNull(value, "Value must not be null!");
        this.execute(name, (connection) -> {
            if (shouldExpireWithin(ttl)) {
                connection.set(key, value, Expiration.from(ttl.toMillis(), TimeUnit.MILLISECONDS), SetOption.upsert());
            } else {
                connection.set(key, value);
            }

            return "OK";
        });
    }

    public byte[] get(String name, byte[] key) {
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(key, "Key must not be null!");
        return this.execute(name, (connection) -> connection.get(key));
    }

    public byte[] putIfAbsent(String name, byte[] key, byte[] value, @Nullable Duration ttl) {
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(key, "Key must not be null!");
        Assert.notNull(value, "Value must not be null!");
        return (byte[])((byte[])this.execute(name, (connection) -> {
            if (this.isLockingCacheWriter()) {
                this.doLock(name, connection);
            }

            byte[] var9;
            try {
                boolean put;
                if (shouldExpireWithin(ttl)) {
                    put = connection.set(key, value, Expiration.from(ttl), SetOption.ifAbsent());
                } else {
                    put = connection.setNX(key, value);
                }

                if (put) {
                    Object var7 = null;
                    return (byte[])((byte[])var7);
                }

                byte[] var11 = connection.get(key);
                var9 = var11;
            } finally {
                if (this.isLockingCacheWriter()) {
                    this.doUnlock(name, connection);
                }

            }

            return var9;
        }));
    }

    public void remove(String name, byte[] key) {
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(key, "Key must not be null!");
        String keyString = new String(key);
        log.info("redis remove key:" + keyString);
        String keyIsAll = "*";
        if (keyString != null && keyString.endsWith(keyIsAll)) {
            this.execute(name, (connection) -> {
                Set<byte[]> keys = connection.keys(key);
                int delNum = 0;

                byte[] keyByte;
                for(Iterator var4 = keys.iterator(); var4.hasNext(); delNum = (int)((long)delNum + connection.del(new byte[][]{keyByte}))) {
                    keyByte = (byte[])var4.next();
                }

                return delNum;
            });
        } else {
            this.execute(name, (connection) -> {
                return connection.del(new byte[][]{key});
            });
        }

    }

    public void clean(String name, byte[] pattern) {
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(pattern, "Pattern must not be null!");
        this.execute(name, (connection) -> {
            boolean wasLocked = false;

            try {
                if (this.isLockingCacheWriter()) {
                    this.doLock(name, connection);
                    wasLocked = true;
                }

                byte[][] keys = (byte[][])((byte[][])((Set)Optional.ofNullable(connection.keys(pattern)).orElse(Collections.emptySet())).toArray(new byte[0][]));
                if (keys.length > 0) {
                    connection.del(keys);
                }
            } finally {
                if (wasLocked && this.isLockingCacheWriter()) {
                    this.doUnlock(name, connection);
                }

            }

            return "OK";
        });
    }

    void lock(String name) {
        this.execute(name, (connection) -> {
            return this.doLock(name, connection);
        });
    }

    void unlock(String name) {
        this.executeLockFree((connection) -> {
            this.doUnlock(name, connection);
        });
    }

    private Boolean doLock(String name, RedisConnection connection) {
        return connection.setNX(createCacheLockKey(name), new byte[0]);
    }

    private Long doUnlock(String name, RedisConnection connection) {
        return connection.del(new byte[][]{createCacheLockKey(name)});
    }

    boolean doCheckLock(String name, RedisConnection connection) {
        return connection.exists(createCacheLockKey(name));
    }

    private boolean isLockingCacheWriter() {
        return !this.sleepTime.isZero() && !this.sleepTime.isNegative();
    }

    private <T> T execute(String name, Function<RedisConnection, T> callback) {
        RedisConnection connection = this.connectionFactory.getConnection();

        T var4;
        try {
            this.checkAndPotentiallyWaitUntilUnlocked(name, connection);
            var4 = callback.apply(connection);
        } finally {
            connection.close();
        }
        return var4;
    }

    private void executeLockFree(Consumer<RedisConnection> callback) {
        RedisConnection connection = this.connectionFactory.getConnection();

        try {
            callback.accept(connection);
        } finally {
            connection.close();
        }

    }

    private void checkAndPotentiallyWaitUntilUnlocked(String name, RedisConnection connection) {
        if (this.isLockingCacheWriter()) {
            try {
                while(this.doCheckLock(name, connection)) {
                    Thread.sleep(this.sleepTime.toMillis());
                }
            } catch (InterruptedException var4) {
                Thread.currentThread().interrupt();
                throw new PessimisticLockingFailureException(String.format("Interrupted while waiting to unlock cache %s", name), var4);
            }
        }

    }

    private static boolean shouldExpireWithin(@Nullable Duration ttl) {
        return ttl != null && !ttl.isZero() && !ttl.isNegative();
    }

    private static byte[] createCacheLockKey(String name) {
        return (name + "~lock").getBytes(StandardCharsets.UTF_8);
    }

    public CacheStatistics getCacheStatistics(String cacheName) {
        return this.statistics.getCacheStatistics(cacheName);
    }

    public void clearStatistics(String name) {
    }

    public RedisCacheWriter withStatisticsCollector(CacheStatisticsCollector cacheStatisticsCollector) {
        return null;
    }
}
