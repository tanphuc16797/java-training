package com.amit.springsercurity.redis.repository;

import com.amit.springsercurity.redis.entity.TokenDTO;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<TokenDTO , String> {
}
