package com.santos.ratelimiter.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.santos.ratelimiter.RateLimiter;
import com.santos.ratelimiter.RateLimiterRegistry;
import com.santos.ratelimiter.algorithm.ConcurrentRateLimiter;
import com.santos.ratelimiter.algorithm.TokenBucketRateLimiter;

public class InMemoryRateLimiterRegistry implements RateLimiterRegistry {

	private final Map<String, RateLimiter> map = new ConcurrentHashMap<>();

	private final long limitForPeriod;
	private final long periodInSeconds;

	public InMemoryRateLimiterRegistry(long limitForPeriod, long periodInSeconds) {
		this.limitForPeriod = limitForPeriod;
		this.periodInSeconds = periodInSeconds;
	}

	@Override
	public RateLimiter reateLimiter(String id) {
		return map.computeIfAbsent(id, (k) -> new ConcurrentRateLimiter(new TokenBucketRateLimiter(this.limitForPeriod, this.periodInSeconds)));
	}

	@Override
	public long getLimitForPeriod() {
		return this.limitForPeriod;
	}

	@Override
	public long getPeriodInSeconds() {
		return this.periodInSeconds;
	}
}
