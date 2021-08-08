package com.amit.redis.queue;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class RedisMessageSubscriberImpl implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] bytes) {
        System.out.println("Queue demo :" + message.toString());
    }
}