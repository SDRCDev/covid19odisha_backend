package com.sdrc.covid19odisha.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdrc.covid19odisha.collections.DashboardPanelOptions;

public interface DashboardPanelRepository extends MongoRepository<DashboardPanelOptions, String> {

	List<DashboardPanelOptions> findByFormId(Integer formId);

	DashboardPanelOptions findByAggregatePath(String path);

}
