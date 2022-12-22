package com.tinnitussounds.cms.album;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AlbumRepository extends MongoRepository<Album, String> {
    @Query("{ 'name' : ?0 }")
    List<Album> findByName(String name);

    @Query("{ 'name': {$regex: ?0, '$options' : 'i'} }")
    List<Album> findBySearch(String pattern);
}
