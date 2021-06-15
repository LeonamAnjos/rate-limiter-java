package com.example.demo.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.santos.ratelimiter.RateLimiter;
import com.santos.ratelimiter.RateLimiterRegistry;

public abstract class RateLimiterFilter {
	private static Logger logger = LoggerFactory.getLogger(RateLimiterFilter.class);

	private RateLimiterRegistry rateLimiterRegistry;

	protected abstract long getPeriodInSeconds();

	protected abstract long getLimitForPeriod();

	protected RateLimiterRegistry getRateLimiterRegistry() {
		if (rateLimiterRegistry == null) {
			logger.info("Rate Limiter Registry of: {} requests per {} seconds", getLimitForPeriod(), getPeriodInSeconds());
			rateLimiterRegistry = RateLimiterRegistry.of(getLimitForPeriod(), getPeriodInSeconds());
		}

		return rateLimiterRegistry;
	}

	protected RateLimiter getRateLimiter(String requester) {
		return getRateLimiterRegistry().rateLimiter(requester);
	}
}
