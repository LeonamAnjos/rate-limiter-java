package com.santos.ratelimiter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.santos.ratelimiter.registry.InMemoryRateLimiterRegistry;

class RateLimiterRegistryTest {


	private static final long limitForPeriod = 2L;
	private static final long periodInSeconds = 1L;
	private RateLimiterRegistry registry;

	@BeforeEach
	void setup() {
		registry = RateLimiterRegistry.of(limitForPeriod, periodInSeconds);
	}

	@Test
	void of_whenDefaultClass() {
		assertEquals(InMemoryRateLimiterRegistry.class, registry.getClass());
	}

	@Test
	void of_whenInformedConfiguration() {
		assertEquals(limitForPeriod, registry.getLimitForPeriod());
		assertEquals(periodInSeconds, registry.getPeriodInSeconds());
	}
}
