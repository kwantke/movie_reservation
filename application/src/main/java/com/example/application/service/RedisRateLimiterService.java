package com.example.application.service;

import com.example.application.port.out.RateLimiterPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class RedisRateLimiterService implements RateLimiterPort {

  private final RedisTemplate<String, String> redisTemplate;

  private static final String  RATE_LIMITER_LUA_SCRIPT =
                  "local key = KEYS[1]; " +
                  "local requests = tonumber(redis.call('GET', key) or '0'); " + // 요청 횟수 (기본 값 0)
                  "local max_requests = tonumber(ARGV[1]); " + // 허용된 최대 요청 횟수
                  "local expiry = tonumber(ARGV[2]); " + // 제한 시간 (초)

                  "if requests >= max_requests then " +
                  "    return 0; " + // 최대 요청 횟수를 초과하면 차단 (false)
                  "end; " +

                  "requests = redis.call('INCR', key); " + // 요청 횟수 증가
                  "if requests == 1 then " +
                  "redis.call('EXPIRE', key, expiry); " + // 최초 요청시에만 TTL 갱신
                  "end; " +

                  "return 1;"; // 요청 허용 (true)

  @Override
  public boolean isAllowed(String key, int maxRquests, int durationInSeconds) { // 키 , 최대 요청 횟수, 제한 시간
    DefaultRedisScript<Long> script = new DefaultRedisScript<>(RATE_LIMITER_LUA_SCRIPT, Long.class);
    Long result = redisTemplate.execute(script, Collections.singletonList(key),
            String.valueOf(maxRquests), String.valueOf(durationInSeconds));

    return result == 1;
  }
}
