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

	@Override
	public long getLimitForPeriod() {
		return this.limitForPeriod;
	}

	@Override
	public long getPeriodInSeconds() {
		return this.periodInSeconds;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TokenBucketRateLimiter other = (TokenBucketRateLimiter) obj;
		if (bucketSize != other.bucketSize)
			return false;
		if (lastRefillTimestamp != other.lastRefillTimestamp)
			return false;
		if (limitForPeriod != other.limitForPeriod)
			return false;
		if (periodInSeconds != other.periodInSeconds)
			return false;
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
