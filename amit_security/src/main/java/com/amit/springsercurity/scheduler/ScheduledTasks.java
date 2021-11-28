package com.amit.springsercurity.scheduler;


import com.amit.springsercurity.domain.ActivityStore;
import com.amit.springsercurity.redis.repository.ActivityRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduledTasks {
    private static final Logger LOGGER = LogManager.getLogger(ScheduledTasks.class);
    @Autowired
    ActivityStore activityStore;
    @Autowired
    ActivityRepository activityRepository;

    @Scheduled(fixedRate = 1*1000, initialDelay = 20*1000)
    public void UpdateActivity(){
        activityStore.updateActivity();
    }
}
