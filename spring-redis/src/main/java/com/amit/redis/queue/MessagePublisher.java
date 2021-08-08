package com.amit.redis.queue;

public interface MessagePublisher {
	void publish(final String message);
}
