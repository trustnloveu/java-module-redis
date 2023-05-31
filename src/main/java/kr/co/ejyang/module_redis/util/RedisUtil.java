package kr.co.ejyang.module_redis.util;

import com.fasterxml.jackson.core.type.TypeReference;
import kr.co.ejyang.module_redis.config.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisUtil {

    private final RedisTemplate<Object,Object> redisTemplate;
    private final RedisConfig redisConfig;

    RedisUtil(
        @Autowired RedisTemplate<Object,Object> redisTemplate,
        @Autowired RedisConfig redisConfig
    ) {
        this.redisTemplate = redisTemplate;
        this.redisConfig = redisConfig;
    }

    // Get Data
    public Map<String, Object> getRedisDataMap(String redisKey) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if(Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
                String data = Objects.requireNonNull(redisTemplate.opsForValue().get(redisKey)).toString();
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
            if(Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
                data = Objects.requireNonNull(redisTemplate.opsForValue().get(redisKey)).toString();
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
            if (Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
                String data = Objects.requireNonNull(redisTemplate.opsForValue().get(redisKey)).toString();
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
            redisTemplate.opsForValue().set(redisKey , value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 삭제
    public void removeRedisData(String redisKey) {
        redisTemplate.delete(redisKey);
    }

    // 등록 + TTL 설정
    public void setRedisDataWithTTL(String redisKey, String value) {
        redisTemplate.opsForValue().set(redisKey, value, redisConfig.getTtl(), TimeUnit.SECONDS);
    }

    // TTL 설정
    public void setRedisTTL(String redisKey) {
        redisTemplate.expire(redisKey, redisConfig.getTtl(), TimeUnit.SECONDS);
    }

}
