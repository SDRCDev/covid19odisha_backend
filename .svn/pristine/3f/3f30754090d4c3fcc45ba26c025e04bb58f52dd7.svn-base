package com.sdrc.covid19odisha.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdrc.covid19odisha.collections.VisitorCount;

public interface VisitorCountRepository  extends MongoRepository<VisitorCount, String> {

	List<VisitorCount> findByIpAddress(String ipAddr);

	List<VisitorCount> findByLastSessionid(String sessionId);

}
