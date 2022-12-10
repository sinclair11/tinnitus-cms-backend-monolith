package com.tinnitussounds.cms.auth;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, String> {
    public Admin findByUser(String user);
}
