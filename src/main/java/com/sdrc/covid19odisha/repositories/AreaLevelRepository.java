package com.sdrc.covid19odisha.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdrc.covid19odisha.collections.AreaLevel;

public interface AreaLevelRepository extends MongoRepository<AreaLevel, String> {

}
