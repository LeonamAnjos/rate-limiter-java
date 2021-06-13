package com.santos.ratelimiter.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.santos.ratelimiter.RateLimiter;
import com.santos.ratelimiter.RateLimiterRegistry;

class InMemoryRateLimiterRegistryTest {

	private static final long limitForPeriod = 2;
	private static final long periodInSeconds = 1;

	private RateLimiterRegistry registry;

	@BeforeEach
	void setup() {
		registry = new InMemoryRateLimiterRegistry(limitForPeriod, periodInSeconds);
	}

	@Test
	void rateLimiter_whenFirstTimeWithId() {
		RateLimiter limiter = registry.reateLimiter("test-id-1");

		assertNotNull(limiter);
		assertEquals(limitForPeriod, limiter.getLimitForPeriod());
		assertEquals(periodInSeconds, limiter.getPeriodInSeconds());
	}

	@Test
	void rateLimiter_whenSecondTimeWithId() {
		RateLimiter limiter1 = registry.reateLimiter("test-id-1");
		RateLimiter limiter2 = registry.reateLimiter("test-id-1");

		assertEquals(limiter1, limiter2);
	}

	@Test
	void getLimitForPeriod_whenTest() {
		assertEquals(limitForPeriod, registry.getLimitForPeriod());
	}

	@Test
	void getPeriodInSeconds_whenTest() {
		assertEquals(periodInSeconds, registry.getPeriodInSeconds());
	}

}
