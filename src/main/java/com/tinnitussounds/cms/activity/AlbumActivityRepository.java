package com.tinnitussounds.cms.activity;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlbumActivityRepository extends MongoRepository<AlbumActivity, String> {
    
}
