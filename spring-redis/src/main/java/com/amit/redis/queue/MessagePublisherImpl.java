package com.amit.redis.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

public class MessagePublisherImpl implements MessagePublisher {
    @Autowired
    RedisTemplate<Integer, Object> redisTemplate;

    @Autowired
    ChannelTopic channelTopic;

    @Override
    public void publish(String message) {
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
    }
    
    public MessagePublisherImpl(RedisTemplate<Integer, Object> redisTemplate2,  ChannelTopic topic) {
        this.redisTemplate = redisTemplate2;
        this.channelTopic = topic;
    }
}
