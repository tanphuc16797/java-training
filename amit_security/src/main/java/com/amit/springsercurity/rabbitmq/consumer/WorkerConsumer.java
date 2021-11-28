package com.amit.springsercurity.rabbitmq.consumer;
import com.amit.springsercurity.domain.ActivityStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class WorkerConsumer implements RabbitListenerConfigurer {
    @Autowired
    private ActivityStore activityStore;
    private static final Logger logger = LogManager.getLogger(WorkerConsumer.class);

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {}

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receiveMessage(String message) {
        if (!activityStore.getIsMaster()){
            doJob(message);
        }
    }

    private void doJob(String message){
        logger.info("user: " + activityStore.getUniqueSessionID() + " received is.. " + message);
    }
}
