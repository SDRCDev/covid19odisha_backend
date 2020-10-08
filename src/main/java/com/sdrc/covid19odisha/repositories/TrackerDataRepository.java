package com.sdrc.covid19odisha.repositories;

import java.util.Date;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdrc.covid19odisha.collections.TrackerData;

public interface TrackerDataRepository extends MongoRepository<TrackerData, String>{

	TrackerData findTop1ByOrderByIdDesc();

	TrackerData findByDbDate(String dates);

}
