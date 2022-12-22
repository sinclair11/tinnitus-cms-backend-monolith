package com.tinnitussounds.cms.sample;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface SampleRepository extends MongoRepository<Sample, String> {
    @Query("{ 'name' : ?0 }")
    List<Sample> findByName(String name);
}
