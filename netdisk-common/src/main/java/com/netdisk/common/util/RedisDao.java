package com.netdisk.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;


@Repository
public class RedisDao {
    @Autowired
    private StringRedisTemplate template;

    public void setKey(String key,String value,Integer timeout){
        ValueOperations<String,String> ops = template.opsForValue();
        try {
            ops.set(key, value, timeout, TimeUnit.HOURS);
        } catch (Exception e){
            System.out.println("Redis 设置值异常");
        }

    }


    public String getValue(String key){
        ValueOperations<String,String> ops = this.template.opsForValue();
        String result = null;
        try{
            result = ops.get(key);
        }catch(Exception e){
            System.out.println("Redis 获取值异常");
        }
        return result;
    }

    public void deleteCash(String token){
        template.delete(token);
    }
    public boolean hasKey(String token){
        return template.hasKey(token);
    }
}
