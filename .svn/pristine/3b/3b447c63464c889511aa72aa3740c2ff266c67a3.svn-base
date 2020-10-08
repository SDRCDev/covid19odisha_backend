package com.sdrc.covid19odisha.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sdrc.covid19odisha.collections.Resources;

public interface ResourcesRepository extends MongoRepository<Resources, String> {
	
	Resources findById(String id);
	
	List<Resources> findBySubmittedByAndIsActiveTrue(String name);
	
	@Query("{'submittedBy':{$in:?0}}")
	List<Resources> findBySubmittedByInAndIsActiveTrue(List<String> name);

	List<Resources> findByIsActiveTrueAndIsApproveTrue();


	
	

}
