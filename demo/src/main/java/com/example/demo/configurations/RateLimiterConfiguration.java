package com.example.demo.configurations;

public abstract class RateLimiterConfiguration {
	private long limitForPeriod;
	private long periodInSeconds;
	private boolean enabled;

	public long getLimitForPeriod() {
		return limitForPeriod;
	}

	public void setLimitForPeriod(long limitForPeriod) {
		this.limitForPeriod = limitForPeriod;
	}

	public long getPeriodInSeconds() {
		return periodInSeconds;
	}

	public void setPeriodInSeconds(long periodInSeconds) {
		this.periodInSeconds = periodInSeconds;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
