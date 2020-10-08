package com.sdrc.covid19odisha.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdrc.covid19odisha.collections.Question;

public interface QuestionRepository extends MongoRepository<Question, String> {

	Question findByFormId(int formId);

}
