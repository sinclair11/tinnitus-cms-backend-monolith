package com.tinnitussounds.cms.preset;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PresetRepository extends MongoRepository<Preset, String> {
    @Query("{ 'name' : ?0 }")
    List<Preset> findByName(String name);

    @Query("{ 'name': {$regex: ?0, '$options' : 'i'} }")
    List<Preset> findBySearch(String pattern);
}
