package com.amit.springsercurity.redis.repository;

import com.amit.springsercurity.redis.entity.Activity;
import org.springframework.data.repository.CrudRepository;

public interface ActivityRepository extends CrudRepository<Activity, String> {
}
