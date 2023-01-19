package com.tinnitussounds.cms.activity;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SampleActivityRepository extends MongoRepository<SampleActivity, String> {
    
}
