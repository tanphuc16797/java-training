package com.amit.springsercurity.scheduler;

import com.amit.springsercurity.domain.ActivityStore;
import com.amit.springsercurity.rabbitmq.producer.MasterProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


@Component
public class MasterTasks {
    @Autowired
    ActivityStore activityStore;

    @Autowired
    MasterProducer masterProducer;

    @Scheduled(fixedDelay = 1*1000, initialDelay = 15*1000)
    public void sendMessage(){
        if (activityStore.getIsMaster()) {
            masterProducer.sendMessage("" + new Timestamp(System.currentTimeMillis()).getTime());
        }
    }
}
