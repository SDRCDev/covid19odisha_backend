package com.sdrc.covid19odisha.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdrc.covid19odisha.collections.Type;

public interface TypeRepository extends MongoRepository<Type, String> {

	Type findByTypeName(String string);

}
