package com.sdrc.covid19odisha.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface SubmissionService {
	public Map<String, Object> saveSubmission(Map<String, Object> data);
	
	public Map<String, Object> validateOTP(String otp, String mobileNumber, String id);
	
	public Integer sendVerificationMessage(Map<String, Object> otpdata);

	ResponseEntity<String> uploadFile(MultipartFile multipartfile);

	public List<Map<String, Object>> getListOfVolunteer();

	public Map<String, Object> getVolunteerDetails(String mobile);

	Map<String, String> getRegistrationStatus(String mobile,Integer languageId);
}
