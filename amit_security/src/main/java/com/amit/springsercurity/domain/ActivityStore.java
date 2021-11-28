package com.amit.springsercurity.domain;

import com.amit.springsercurity.redis.entity.Activity;
import com.amit.springsercurity.redis.repository.ActivityRepository;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class ActivityStore {
    @Autowired
    private ApplicationContext appContext;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    RedissonClient redisson;

    String uniqueSessionID = "";

    public static Boolean isMaster = false;

    @Value("${activity.time-to-live}")
    long activityTimeToLive;

    @Transactional
    public void updateActivity(){
        Activity activity = new Activity();
        activity.setLogin(getUniqueSessionID());
        activity.setTimeToLive(activityTimeToLive);
        if (!MasterIsExisted()) {
            try {
                RLock rLock = redisson.getFairLock("admin");
                rLock.lockInterruptibly(10, TimeUnit.SECONDS);
                isMaster = true;
                uniqueSessionID = "master";
                activity.setLogin(uniqueSessionID);
            } catch (InterruptedException e) {}
        }
        activityRepository.save(activity);
    }

    public Boolean MasterIsExisted(){
        return activityRepository.findById("master").isPresent();
    }

    public Boolean getIsMaster(){
        return isMaster;
    }

    public String getUniqueSessionID(){
        if (uniqueSessionID.isEmpty()){
            uniqueSessionID = UUID.randomUUID().toString();
        }
        return uniqueSessionID;
    }
}
