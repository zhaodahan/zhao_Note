package com.ec.changan.cb.utils;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


public class RedisLock {
	private static Logger logger = LoggerFactory.getLogger(RedisLock.class);
	
	@SuppressWarnings("rawtypes")
	private RedisTemplate redisTemplate;

    private String lockKey;

    /**
     * 锁超时时间，防止异常的锁占用
     */
    private int expireMsecs = 60 * 1000;

    /**
     * 锁等待时间
     */
    private int timeoutMsecs = 2 * 1000;

    private volatile boolean locked = false;

    @SuppressWarnings("rawtypes")
	public RedisLock(RedisTemplate redisTemplate, String lockKey) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey + "_lock";
    }

    @SuppressWarnings("rawtypes")
	public RedisLock(RedisTemplate redisTemplate, String lockKey, int timeoutMsecs) {
        this(redisTemplate, lockKey);
        this.timeoutMsecs = timeoutMsecs;
    }

    @SuppressWarnings("rawtypes")
	public RedisLock(RedisTemplate redisTemplate, String lockKey, int timeoutMsecs, int expireMsecs) {
        this(redisTemplate, lockKey, timeoutMsecs);
        this.expireMsecs = expireMsecs;
    }

    /**
     * 获取锁关键字
     */
    public String getLockKey() {
        return lockKey;
    }

    /**
     * 从Redis获取key对应的value
     * @param key		-	key值
     * @return value值
     */
    @SuppressWarnings("unchecked")
	private String get(final String key) {
        Object obj = null;
        try {
            obj = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    byte[] data = connection.get(serializer.serialize(key));
                    connection.close();
                    if (data == null) {
                        return null;
                    }
                    return serializer.deserialize(data);
                }
            });
        } catch (Exception e) {
        	logger.error("从Redis获取数据(get)异常,key={},error={}", key,e.getMessage());
        }
        return obj != null ? obj.toString() : null;
    }

    /**
     * 向Redis设置key对应的value
     * @param key		-	key值
     * @param value		-	value值
     * @return 成功或失败
     */
    @SuppressWarnings("unchecked")
	private boolean setNX(final String key, final String value) {
        Object obj = null;
        try {
            obj = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    Boolean success = connection.setNX(serializer.serialize(key), serializer.serialize(value));
                    connection.close();
                    return success;
                }
            });
        } catch (Exception e) {
            logger.error("向Redis设置数据(setNX)异常,key={},error={}", key,e.getMessage());
        }
        return obj != null ? (Boolean) obj : false;
    }

    /**
     * 从Redis获取并设置key对应的value
     * @param key		-	key值
     * @param value		-	新的value值
     * @return 旧的value值，如无旧值返回空
     */
    @SuppressWarnings("unchecked")
	private String getSet(final String key, final String value) {
        Object obj = null;
        try {
            obj = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    byte[] ret = connection.getSet(serializer.serialize(key), serializer.serialize(value));
                    connection.close();
                    return serializer.deserialize(ret);
                }
            });
        } catch (Exception e) {
            logger.error("从Redis获取并设置数据(getSet)异常,key={},error={}", key,e.getMessage());
        }
        return obj != null ? (String) obj : null;
    }
    
    /**
     * 从Redis删除key对应的value
     * @param key		-	key值
     * @return 删除键值结果	0 - 未删除,1 - 已删除。
     */
    @SuppressWarnings("unchecked")
	private Long delete(final String key) {
        Object obj = null;
        try {
            obj = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    Long result = connection.del(serializer.serialize(key));
                    connection.close();
                    return result;
                }
            });
        } catch (Exception e) {
            logger.error("从Redis获删除数据(del)异常,key={},error={}", key,e.getMessage());
        }
        return obj != null ? (Long) obj : null;
    }

    /**
     * 获得锁-在Redis中创建键值，会等待timeoutMsecs时间后返回
     * @return 获取锁的结果，true - 成功；false - 失败
     */
    public boolean lock(){
        int timeout = timeoutMsecs;
        Random random = new Random(); 
        
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            String expiresStr = String.valueOf(expires);//锁到期时间
            if (this.setNX(lockKey, expiresStr)) {
                locked = true;
                return true;
            }
            String currentValueStr = this.get(lockKey);//redis里保存的锁超时时间
            //判断锁是否超时
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()){
            	//锁超时，尝试重新设置锁的超时时间
                logger.info("lock超时");
                String oldValueStr = this.getSet(lockKey, expiresStr);
                //判断是否为第一个抢占重置锁的超时时间的竞争者
                if(oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    //时间是最初获取到的超时时间，表示是第一个抢占重置锁的超时时间的竞争者（获胜竞争者）
                	//失败竞争者，会刷新锁的超时时间，使得锁的超时时间比获胜竞争者设置的超时时间往后推迟一点点（毫秒级）
                    logger.info("lock超时后重置");
                    locked = true;
                    return true;
                }
            }
            int waitMilllis = random.nextInt(100);
            timeout -= waitMilllis;
            logger.debug("lock timeout = {}" , timeout);
            try {
				Thread.sleep(waitMilllis);
			} catch (InterruptedException e) {
                //logger.info("Interrupted! {}",e);
                //Thread.currentThread().interrupt();
                logger.error("lock异常：{}", e);
			}
        }
        return false;
    }

    /**
     * 释放锁-在Redis中删除键值
     */
    public void unlock() {
        if (locked) {
        	this.delete(lockKey);
            locked = false;
        }
    }
    
    /**
     * 测试锁当前状态-但不在Redis中创建键值，立即返回
     * @return 锁的当前状态 0 - 无锁；1 - 锁存在未超时；2 - 锁存在且超时
     */
	public synchronized int testLock(){
		int	lockStatus = 0;
		
		String currentValueStr = this.get(lockKey);//redis里保存的锁超时时间
        //判断锁是否超时
        if (currentValueStr != null){
        	lockStatus = 1;
        	 if(Long.parseLong(currentValueStr) < System.currentTimeMillis()){
        		 lockStatus = 2;
        	 }
        }else{
        	//返回空表示并无此锁
        	lockStatus = 0;
        }
		return lockStatus;
	}
	
	/**
	 * 续期锁的超时时间
	 * @return 续期锁的结果，true - 成功；false - 失败
	 */
	public boolean renewLock(){
		String currentValueStr = this.get(lockKey);//redis里保存的锁超时时间
		if(currentValueStr != null){
			long expires = System.currentTimeMillis() + expireMsecs + 1;
            String expiresStr = String.valueOf(expires);//锁到期时间
            
			String oldValueStr = this.getSet(lockKey, expiresStr);
			if(oldValueStr != null){
				if(oldValueStr.equals(currentValueStr)){
					return true;
				}else{
                    logger.error("lock续期存在竞争");
					return true;
				}
			}
		}
		return false;
	}
}
