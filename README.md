# java-module-redis
Redis 연동 및 관련 유틸 모듈


## 실행환경
+ 프로그래밍 언어 : Java 17.0.7
+ 프레임워크 : Spring Boot 3.0.5
+ 저장소 관리 : Gradle 7.4


## 프로젝트 구성
```shell
module-redis
└── src
    └── main
        ├── java.kr.co.ejyang.module_redis
        │   ├── config
        │   │   └── RedisConfig.java
        │   ├── util
        │   │   ├── JsonUtil.java
        │   │   └── RedisUtil.java
        │   └── MainApplication.java
        └── resources
            └── application.properties
```


## 호출  ( RedisUtil.java )
+ `getRedisDataMap` : Map 데이터 가져오기
+ `getRedisDataString` : String 데이터 가져오기
+ `getRedisDataList` : List 데이터 가져오기
+ `setRedisData` : 데이터 등록
+ `setRedisDataWithTTL` : 데이터 등록 - 유효시간 설정
+ `removeRedisData` : 데이터 삭제
+ `setRedisTTL` : 기존 등록 데이터에 유효시간 부여


## Redis 설정 관리 ( RedisConfig.java )
+ `ip` : IP 환경변수
+ `port` : Port 환경변수
+ `ttl` : 유효시간 환경변수
+ `redisTemplate` : Redis 템플릿 설정
+ `redisConnectionFactory` : 커넥션 관리 ( ip, port )
+ `jedisPoolConfig` : 커넥션풀 관리