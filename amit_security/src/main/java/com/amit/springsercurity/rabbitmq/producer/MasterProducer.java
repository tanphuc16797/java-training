package com.amit.springsercurity.rabbitmq.producer;

import com.amit.springsercurity.domain.ActivityStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MasterProducer {
    private static final Logger logger = LogManager.getLogger(MasterProducer.class);
    private RabbitTemplate rabbitTemplate;
    @Value("${spring.rabbitmq.exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.routingKey}")
    private String routingKey;
    @Autowired
    ActivityStore activityStore;

    @Autowired
    public MasterProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message){
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        logger.info(activityStore.getUniqueSessionID() + " has been sent message " + message);
    }

}
