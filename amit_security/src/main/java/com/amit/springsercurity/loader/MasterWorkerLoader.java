package com.amit.springsercurity.loader;

import com.amit.springsercurity.domain.ActivityStore;
import com.amit.springsercurity.redis.repository.ActivityRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class MasterWorkerLoader implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger(MasterWorkerLoader.class);
    @Autowired
    ActivityStore activityStore;

    @Autowired
    ActivityRepository activityRepository;

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    @Transactional
    private void loadData() {
        activityStore.updateActivity();
    }
}
