package com.tinnitussounds.cms.activity;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PresetActivityRepository extends MongoRepository<PresetActivity, String> {
    
}
