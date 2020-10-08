package com.sdrc.covid19odisha.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdrc.covid19odisha.collections.CmsType;

public interface CmsTypeRepository extends MongoRepository<CmsType, String> {

}
