package com.sdrc.covid19odisha.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sdrc.covid19odisha.collections.SubmissionData;



public interface SubmissionRepository extends MongoRepository<SubmissionData, String> {

	@Query("{'data.mobile' : ?0, 'isVerified' : true}")
	SubmissionData getSubmissionByMobileNumber(String valueOf);

	SubmissionData findBy_id(String id);

	List<SubmissionData> findByIsVerifiedTrue();
	
	List<SubmissionData> findByIsVerifiedTrueAndFormId(Integer formId);

	List<SubmissionData> findByFormId(int i);

	List<SubmissionData> findByFormIdAndIsVerifiedTrue(Integer formId);

	List<SubmissionData> findByFormIdAndCreatedDateBetweenAndIsVerifiedTrue(Integer formId, Date startDate,
			Date endDate);

	@Query("{'isVerified' : true,'formId' : 1, ?0 : ?1}")
	List<SubmissionData> getVolunteersListForTaskComponent(String areaLevel, Integer areaId);

	@Query("{'isVerified' : true,'formId' : 2, '$or' : [{?0 : ?1}, {'data.areaOfService' : 105}]}")
	List<SubmissionData> getOrganizationsForTaskComponent(String areaLevel, Integer areaId);
	
	@Query("{'isVerified' : true,'formId' : ?0, 'data.deactivated' : {'$ne' : true}}")
	List<SubmissionData> getStateReportData(Integer formId);

}
