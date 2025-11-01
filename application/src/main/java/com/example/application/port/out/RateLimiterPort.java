package com.example.application.port.out;

public interface RateLimiterPort {

  boolean isAllowed(String key, int maxRquestsPerMinute, int durationInSeconds);
}
