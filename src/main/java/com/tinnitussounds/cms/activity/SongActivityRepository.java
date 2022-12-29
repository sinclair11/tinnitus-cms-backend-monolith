package com.tinnitussounds.cms.activity;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SongActivityRepository extends MongoRepository<SongActivity, String> {
    
}
