package com.sdrc.covid19odisha.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdrc.covid19odisha.collections.CMSData;

public interface CmsDataRepository extends MongoRepository<CMSData, String> {

	List<CMSData> findByCmsCode(String cmsCode);

	CMSData findById(String id);

}
