package com.santos.ratelimiter.algorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.santos.ratelimiter.RateLimiter;

class TokenBucketRateLimiterTest {

	private static final long limitForPeriod = 3;
	private static final long periodInSeconds = 1;
	private RateLimiter limiter;

	@BeforeEach
	void setup() {
		limiter = new TokenBucketRateLimiter(limitForPeriod, periodInSeconds);
	}

	@Test
	void allowRequest_whenLimitForPeriodIsRespected() {
		for(int i = 0; i < limitForPeriod; i++) {
			assertTrue(limiter.allowRequest());
		}
	}

	@Test
	void allowRequest_whenLimitForPeriodIsNotRespected() {
		for(int i = 0; i < limitForPeriod; i++)
			limiter.allowRequest();

		assertFalse(limiter.allowRequest());
	}

	@Test
	void allowRequest_whenBucketIsRefilled() throws InterruptedException {
		for(int i = 0; i < limitForPeriod + 1; i++)
			limiter.allowRequest();

		TimeUnit.SECONDS.sleep(1);

		assertTrue(limiter.allowRequest());
	}

	@Test
	void allowRequest_whenPeriodInSecondsIsInvalid() throws InterruptedException {
		RateLimiter limiter = new TokenBucketRateLimiter(1, 0);

		assertTrue(limiter.allowRequest());
		assertFalse(limiter.allowRequest());

		TimeUnit.SECONDS.sleep(1);

		assertFalse(limiter.allowRequest());
	}

	@Test
	void allowRequest_whenlimitForPeriodIsInvalid() throws InterruptedException {
		RateLimiter limiter = new TokenBucketRateLimiter(0, 1);

		assertFalse(limiter.allowRequest());
		assertFalse(limiter.allowRequest());

		TimeUnit.SECONDS.sleep(1);

		assertFalse(limiter.allowRequest());
	}

	@Test
	void getLimitFroPeriod_whenTest() {
		assertEquals(limitForPeriod, limiter.getLimitForPeriod());
	}

	@Test
	void getPeriodInSeconds_whenTest() {
		assertEquals(periodInSeconds, limiter.getPeriodInSeconds());
	}

	@Test
	void equals_whenSameInstance() {
		assertTrue(limiter.equals(limiter));
	}

	@Test
	void equals_whenDifferentInstances() {
		RateLimiter otherLimiter = new TokenBucketRateLimiter(limitForPeriod, periodInSeconds);
		assertFalse(limiter.equals(otherLimiter));
	}
}
