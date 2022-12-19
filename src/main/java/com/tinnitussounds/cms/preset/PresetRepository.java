package com.tinnitussounds.cms.preset;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PresetRepository extends MongoRepository<Preset, String> {
    
}
