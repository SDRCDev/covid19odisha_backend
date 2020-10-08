package com.sdrc.covid19odisha.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sdrc.covid19odisha.collections.Task;

public interface TaskRepository extends MongoRepository<Task, String> {

	@Query("{'data.mobile' : ?0}")
	List<Task> getByMobileNumber(String mobile);

}
