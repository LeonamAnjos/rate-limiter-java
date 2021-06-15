package com.santos.ratelimiter.algorithm;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import com.santos.ratelimiter.RateLimiter;

public class TokenBucketRateLimiter implements RateLimiter {

	private final long limitForPeriod;
	private final long periodInSeconds;

	private BigDecimal refillFactor;
	private long bucketSize;
	private long lastRefillTimestamp;

	public TokenBucketRateLimiter(long limitForPeriod, long periodInSeconds) {
		super();
		this.limitForPeriod = limitForPeriod;
		this.periodInSeconds = periodInSeconds;
		this.refillFactor = calcRefillFactor(limitForPeriod, periodInSeconds);
		this.addToBucket(limitForPeriod);
	}

	@Override
	public boolean allowRequest() {
		this.addToBucket(this.calcRefill());
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
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;

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

	private long calcRefill() {
		long now = System.nanoTime();
		long elapsedTime = now - this.lastRefillTimestamp;

		return valueOf(elapsedTime).multiply(refillFactor).longValue();
	}

	private void addToBucket(long tokens) {
		if (tokens <= 0)
			return;

		this.bucketSize = Math.min(this.bucketSize + tokens, this.limitForPeriod);
		this.lastRefillTimestamp = System.nanoTime();
	}

	private BigDecimal calcRefillFactor(long limitForPeriod, long periodInSeconds) {
		if (periodInSeconds <= 0)
			return ZERO;

		return valueOf(limitForPeriod)
				.divide(valueOf(periodInSeconds))
				.divide(valueOf(TimeUnit.SECONDS.toNanos(1)));
	}

}
