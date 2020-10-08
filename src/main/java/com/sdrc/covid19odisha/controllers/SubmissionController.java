package com.sdrc.covid19odisha.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sdrc.covid19odisha.services.SubmissionService;

@RestController
public class SubmissionController {
	@Autowired
	private SubmissionService submissionService;
	
//	This API recieves user's profile image from the submission
	@ResponseBody
	@RequestMapping(value = "/uploadProfileImage", method=RequestMethod.POST)
	public ResponseEntity<String> uploadImage(@RequestBody MultipartFile multipartfile) {
		return submissionService.uploadFile(multipartfile);
	}
	
	//Returns the list of volunteers to render in table
	@GetMapping("/getVolunteers")
	public Map<String, List<?>> getListOfVolunteer() {
		Map<String, List<?>> resp = new HashMap<String, List<?>>();
		// Defining table headers
		List<String> headers = Arrays.asList("Name#ନାମ ","Mobile#ମୋବାଇଲ୍ ","Preferred Activity#ପସନ୍ଦର କାମ"  );
		List<Map<String, Object>> volunteers = submissionService.getListOfVolunteer();
		resp.put("tableColumn", headers);
		resp.put("tableData", volunteers);
		return resp;
	}
	
	//Returns a specific volunteer given the mobile number
	@GetMapping("/getVolunteerDetails")
	public Map<String, Object> getVolunteerDetails(String mobile) {
		return submissionService.getVolunteerDetails(mobile);
	}
	
	//Checks and returns if a mobile number is already registered or not
	@GetMapping("/getRegistrationStatus")
	public Map<String, String> getRegistrationStatus(String mobile,Integer languageId) {
		return submissionService.getRegistrationStatus(mobile,languageId);
	}
	
	//Saves the registration data for volunteers and organizations
	@PostMapping("/saveSubmission")
	public Map<String, Object> saveSubmission(@RequestBody Map<String, Object> data) {
		return submissionService.saveSubmission(data);
	}
	
	//Validates an OTP
	@PostMapping("/validateOTP")
	public Map<String, Object> validateOTP(@RequestBody Map<String, Object> otpdata) {
		return submissionService.validateOTP(String.valueOf(otpdata.get("otp")), String.valueOf(otpdata.get("mobile")), String.valueOf(otpdata.get("id")));
	}
	
	//Sends the OTP to registered number
	@PostMapping("/resendOTP")
	public Integer resendOTP(@RequestBody Map<String, Object> otpdata) {
		return submissionService.sendVerificationMessage(otpdata);
	}
}
