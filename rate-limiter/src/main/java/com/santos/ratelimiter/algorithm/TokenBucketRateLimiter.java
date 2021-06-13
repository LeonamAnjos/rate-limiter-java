package com.santos.ratelimiter.algorithm;

import java.util.concurrent.TimeUnit;

import com.santos.ratelimiter.RateLimiter;

public class TokenBucketRateLimiter implements RateLimiter {

	private final long limitForPeriod;
	private final long periodInSeconds;
	
	private long bucketSize;
	private long lastRefillTimestamp;
	
	public TokenBucketRateLimiter(long limitForPeriod, long periodInSeconds) {
		super();
		this.limitForPeriod = limitForPeriod;
		this.periodInSeconds = periodInSeconds;
		
		this.addToBucket(limitForPeriod);
	}

	@Override
	public boolean allowRequest() {
		this.refill();
		if (this.bucketSize <= 0)
			return false;
		
		this.bucketSize--;
		return true;
	}

	private void refill() {
		long now = System.nanoTime();
		long elapsedTime = now - this.lastRefillTimestamp;
		long tokens = elapsedTime * (this.limitForPeriod / this.periodInSeconds) / TimeUnit.SECONDS.toNanos(1);
		this.addToBucket(tokens);
	}

	private void addToBucket(long tokens) {
		if (tokens <= 0)
			return;
		
		this.bucketSize = Math.min(this.bucketSize + tokens, this.limitForPeriod);
		this.lastRefillTimestamp = System.nanoTime();
	}
}
