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

    // 생성자
    RedisUtil(@Autowired RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }

    /*******************************************************************************************
     * Map 데이터 가져오기
     *******************************************************************************************/
    public Map<String, Object> getRedisDataMap(String redisKey) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (Boolean.TRUE.equals(redisConfig.redisTemplate().hasKey(redisKey))) {
            String data = Objects.requireNonNull(redisConfig.redisTemplate().opsForValue().get(redisKey)).toString();

            map = JsonUtil.fromJson(data, new TypeReference<Map<String, Object>>() {
            });
        }
        return map;
    }

    /*******************************************************************************************
     * String 데이터 가져오기
     *******************************************************************************************/
    public String getRedisDataString(String redisKey) {
        String data = "";

        if (Boolean.TRUE.equals(redisConfig.redisTemplate().hasKey(redisKey))) {
            data = Objects.requireNonNull(redisConfig.redisTemplate().opsForValue().get(redisKey)).toString();
        }

        return data;
    }

    /*******************************************************************************************
     * List 데이터 가져오기
     *******************************************************************************************/
    public List<Map<String, Object>> getRedisDataList(String redisKey) {
        List<Map<String, Object>> list = new ArrayList<>();

        if (Boolean.TRUE.equals(redisConfig.redisTemplate().hasKey(redisKey))) {
            String data = Objects.requireNonNull(redisConfig.redisTemplate().opsForValue().get(redisKey)).toString();

            list = JsonUtil.fromJson(data, new TypeReference<List<Map<String, Object>>>() {
            });
        }
        return list;
    }

    /*******************************************************************************************
     * 데이터 등록
     *******************************************************************************************/
    public void setRedisData(String redisKey, String value) {
        redisConfig.redisTemplate().opsForValue().set(redisKey, value);
    }

    /*******************************************************************************************
     * 데이터 등록 - 유효시간 설정
     *******************************************************************************************/
    public void setRedisDataWithTTL(String redisKey, String value) {
        redisConfig.redisTemplate().opsForValue().set(redisKey, value, redisConfig.getTtl(), TimeUnit.SECONDS);
    }

    /*******************************************************************************************
     * 데이터 삭제
     *******************************************************************************************/
    public void removeRedisData(String redisKey) {
        redisConfig.redisTemplate().delete(redisKey);
    }

    /*******************************************************************************************
     * 기존 등록 데이터에 유효시간 부여
     *******************************************************************************************/
    public void setRedisTTL(String redisKey) {
        redisConfig.redisTemplate().expire(redisKey, redisConfig.getTtl(), TimeUnit.SECONDS);
    }

}