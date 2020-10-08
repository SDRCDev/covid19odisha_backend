package com.sdrc.covid19odisha.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdrc.covid19odisha.collections.Type;
import com.sdrc.covid19odisha.collections.TypeDetail;

public interface TypeDetailRepository extends MongoRepository<TypeDetail, String> {

	List<TypeDetail> findByTypeId(Integer typeId);

	List<TypeDetail> findByOrderByOrderLevelAsc();

	TypeDetail findBySlugId(int parseInt);

	List<TypeDetail> findByTypeIdOrderByOrderLevelAsc(Integer slugId);

	
}
