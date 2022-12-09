package com.tinnitussounds.cms.auth;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthRepository extends MongoRepository<Auth, String> {
    public Auth findByUser(String user);
}
