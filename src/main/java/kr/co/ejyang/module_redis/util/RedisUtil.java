package kr.co.ejyang.module_redis.util;

import com.fasterxml.jackson.core.type.TypeReference;
import kr.co.ejyang.module_redis.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component("RedisUtil")
public class RedisUtil {

    private final RedisConfig redisConfig;

    RedisUtil(@Autowired RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }

    // Get Data
    public Map<String, Object> getRedisDataMap(String redisKey) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if(Boolean.TRUE.equals(redisConfig.redisTemplate().hasKey(redisKey))) {
                String data = Objects.requireNonNull(redisConfig.redisTemplate().opsForValue().get(redisKey)).toString();
                map = JsonUtil.fromJson(data, new TypeReference<Map<String, Object>>(){});
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public String getRedisDataString(String redisKey) {
        String data = "";
        try {
            if(Boolean.TRUE.equals(redisConfig.redisTemplate().hasKey(redisKey))) {
                data = Objects.requireNonNull(redisConfig.redisTemplate().opsForValue().get(redisKey)).toString();
                System.out.println("data = " + data);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    // Get List Data
    public List<Map<String, Object>> getRedisDataList(String redisKey) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            if (Boolean.TRUE.equals(redisConfig.redisTemplate().hasKey(redisKey))) {
                String data = Objects.requireNonNull(redisConfig.redisTemplate().opsForValue().get(redisKey)).toString();
                list = JsonUtil.fromJson(data, new TypeReference<List<Map<String, Object>>>() {
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 등록
    public void setRedisData(String redisKey, String value) {
        try {
            redisConfig.redisTemplate().opsForValue().set(redisKey , value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 삭제
    public void removeRedisData(String redisKey) {
        redisConfig.redisTemplate().delete(redisKey);
    }

    // 등록 + TTL 설정
    public void setRedisDataWithTTL(String redisKey, String value) {
        redisConfig.redisTemplate().opsForValue().set(redisKey, value, redisConfig.getTtl(), TimeUnit.SECONDS);
    }

    // TTL 설정
    public void setRedisTTL(String redisKey) {
        redisConfig.redisTemplate().expire(redisKey, redisConfig.getTtl(), TimeUnit.SECONDS);
    }

}