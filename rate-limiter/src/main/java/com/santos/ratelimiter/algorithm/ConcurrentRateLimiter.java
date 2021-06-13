package com.santos.ratelimiter.algorithm;

import com.santos.ratelimiter.RateLimiter;

public class ConcurrentRateLimiter implements RateLimiter {

	private final RateLimiter limiter;

	public ConcurrentRateLimiter(RateLimiter limiter) {
		super();
		this.limiter = limiter;
	}

	@Override
	public synchronized boolean allowRequest() {
		return limiter.allowRequest();
	}

}
