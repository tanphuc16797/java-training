package com.amit.redis.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.amit.redis.queue.MessagePublisher;
import com.amit.redis.queue.MessagePublisherImpl;
import com.amit.redis.queue.RedisMessageSubscriberImpl;

@Configuration
public class RedisConfiguration {
	@Bean
    JedisConnectionFactory jedisConnectionFactory(){
        return new JedisConnectionFactory();
    }

    @Bean
     RedisTemplate<Integer, Object> redisTemplate(){
        final RedisTemplate<Integer, Object> template = new RedisTemplate<Integer, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        return template;
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("message");
    }
    
	@Bean
	MessageListenerAdapter messageListenerAdapter(){
	    return new MessageListenerAdapter(new RedisMessageSubscriberImpl());
	}
	
	@Bean
	RedisMessageListenerContainer redisContainer() {
	    final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
	    container.setConnectionFactory(jedisConnectionFactory());
	    container.addMessageListener(messageListenerAdapter(), topic());
	    return container;
	}
	
	@Bean
	MessagePublisher redisPublisher() {
	    return new MessagePublisherImpl(redisTemplate(), topic());
	}
}
