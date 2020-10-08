package com.sdrc.covid19odisha.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sdrc.covid19odisha.collections.OTPToken;

public interface OTPRepository extends MongoRepository<OTPToken, String> {
	
	@Query("{ 'mobileNumber' : ?0, 'otp' : ?1, 'isValid' : true}")
	public OTPToken getOtp(String mobileNumber, String otp);

	public OTPToken findByMobileNumberAndIsValid(String valueOf, boolean b);

}
