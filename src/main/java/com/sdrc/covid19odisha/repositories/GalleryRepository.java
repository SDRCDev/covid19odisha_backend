package com.sdrc.covid19odisha.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sdrc.covid19odisha.collections.Gallery;

public interface GalleryRepository  extends MongoRepository<Gallery, String> {

	Gallery findById(String id);

	@Query("{'submittedBy':{$in:?0}}")
	List<Gallery> findBySubmittedByInAndIsActiveTrue(List<String> userName);

	List<Gallery> findBySubmittedByAndIsActiveTrue(String name);

	List<Gallery> findByIsActiveTrueAndIsApproveTrue();

}
