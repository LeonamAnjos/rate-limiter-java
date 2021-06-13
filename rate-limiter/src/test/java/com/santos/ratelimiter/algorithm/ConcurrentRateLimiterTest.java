package com.santos.ratelimiter.algorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.santos.ratelimiter.RateLimiter;

class ConcurrentRateLimiterTest {

	private RateLimiter rateLimiterMock;
	private RateLimiter limiter;

	@BeforeEach
	void setup() {
		rateLimiterMock = mock(RateLimiter.class);
		limiter = new ConcurrentRateLimiter(rateLimiterMock);
	}
	
	@Test
	void allowRequest_whenAllowed() {
		when(rateLimiterMock.allowRequest()).thenReturn(true);
		
		assertTrue(limiter.allowRequest());
		
		verify(rateLimiterMock, times(1)).allowRequest();
	}

	@Test
	void allowRequest_whenNotAllowed() {
		when(rateLimiterMock.allowRequest()).thenReturn(false);
		
		assertFalse(limiter.allowRequest());
		
		verify(rateLimiterMock, times(1)).allowRequest();
	}
	
	@Test
	void getLimitForPeriod_whenTest() {
		final long limitForPeriod = 2L;
		when(rateLimiterMock.getLimitForPeriod()).thenReturn(limitForPeriod);
		
		assertEquals(limitForPeriod, limiter.getLimitForPeriod());
		
		verify(rateLimiterMock, times(1)).getLimitForPeriod();
	}
	
	@Test
	void getPeriodInSeconds_whenTest() {
		final long periodInSeconds = 1L;
		when(rateLimiterMock.getPeriodInSeconds()).thenReturn(periodInSeconds);
		
		assertEquals(periodInSeconds, limiter.getPeriodInSeconds());
		
		verify(rateLimiterMock, times(1)).getPeriodInSeconds();
	}

}
