package com.sdrc.covid19odisha.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sdrc.covid19odisha.collections.ImportantLinks;

public interface ImportantLinksRepository extends MongoRepository<ImportantLinks, String> {

	ImportantLinks findById(String id);

	@Query("{'submittedBy':{$in:?0}}")
	List<ImportantLinks> findBySubmittedByInAndIsActiveTrue(List<String> userName);

	List<ImportantLinks> findBySubmittedByAndIsActiveTrue(String name);

	List<ImportantLinks> findByIsActiveTrueAndIsApproveTrue();

	List<ImportantLinks> findByIsActiveTrueAndIsApproveTrueOrderByIdDesc();

}
