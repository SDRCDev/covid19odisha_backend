package com.sdrc.covid19odisha.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdrc.covid19odisha.collections.Area;

public interface AreaRepository extends MongoRepository<Area, String> {
	public List<Area> findByAreaLevelAreaLevelIdAndParentAreaId(Integer areaLevelId, Integer parentAreaId);

	public List<Area> findByAreaIdIn(List<Integer> mappedAreaIds);
}
