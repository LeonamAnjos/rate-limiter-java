package com.santos.ratelimiter;

import com.santos.ratelimiter.registry.InMemoryRateLimiterRegistry;

public interface RateLimiterRegistry {

	static RateLimiterRegistry of(long limitForPeriod, long periodInSeconds) {
		return new InMemoryRateLimiterRegistry(limitForPeriod, periodInSeconds);
	}

	RateLimiter rateLimiter(String id);

	long getLimitForPeriod();

	long getPeriodInSeconds();

}
