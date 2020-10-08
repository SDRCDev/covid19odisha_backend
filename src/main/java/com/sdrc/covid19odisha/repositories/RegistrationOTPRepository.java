package com.sdrc.covid19odisha.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdrc.covid19odisha.collections.RegistrationOTP;

public interface RegistrationOTPRepository extends MongoRepository<RegistrationOTP, String> {

	RegistrationOTP findByEmailIdAndIsActiveTrue(String email);

	RegistrationOTP findByEmailIdAndVarificationCodeAndIsActiveTrue(String email, Integer varificationCode);

}
