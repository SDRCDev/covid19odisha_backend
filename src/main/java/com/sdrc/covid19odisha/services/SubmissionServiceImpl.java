package com.sdrc.covid19odisha.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.sdrc.usermgmt.mongodb.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sdrc.covid19odisha.collections.OTPToken;
import com.sdrc.covid19odisha.collections.SubmissionData;
import com.sdrc.covid19odisha.models.UserDetails;
import com.sdrc.covid19odisha.repositories.CustomAccountRepository;
import com.sdrc.covid19odisha.repositories.OTPRepository;
import com.sdrc.covid19odisha.repositories.SubmissionRepository;
import com.sdrc.covid19odisha.repositories.TypeDetailRepository;
import com.sdrc.covid19odisha.utils.ImageConverter;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SubmissionServiceImpl implements SubmissionService {

	@Autowired
	private SubmissionRepository submissionRepository;

	@Autowired
	private ConfigurableEnvironment configurableEnvironment;

	@Autowired
	private OTPRepository otpRepository;

	@Autowired
	private TypeDetailRepository typeDetailRepository;

	@Autowired
	@Qualifier("customAccountRepository")
	private CustomAccountRepository customAccountRepository;

	@Value("${volunteer.registration.imagepath}")
	private String photoIdfilepath;

	private Path photoIdfilePathLocation;

	@PostConstruct
	public void init() {
		photoIdfilePathLocation = Paths.get(photoIdfilepath + "profilepics/");
	}

	@Override
	public ResponseEntity<String> uploadFile(MultipartFile multipartfiles) {
		String filePath = null;
		String fileNameWithDateTime = null;
		// Creating directory
		if (!new File(photoIdfilepath + "profilepics/").exists()) {
			new File(photoIdfilepath + "profilepics/").mkdirs();
		}
		if (multipartfiles != null) {
			try {
				// Concating date and time with filename
				fileNameWithDateTime = FilenameUtils.getBaseName(multipartfiles.getOriginalFilename()) + "_"
						+ new Date().getTime() + "." + FilenameUtils.getExtension(multipartfiles.getOriginalFilename());
				// Concating filename with filepath
				filePath = photoIdfilepath + "profilepics/" + fileNameWithDateTime;
				// Copying file from multipart stream to filepath
				Files.copy(multipartfiles.getInputStream(), this.photoIdfilePathLocation.resolve(fileNameWithDateTime),
						StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Action : while upload profile pics with payload {}", fileNameWithDateTime, e);
			}
		}
		return new ResponseEntity<>(fileNameWithDateTime, HttpStatus.OK);
	}

	// Saves the registration data for volunteers and organizations
	@Override
	public Map<String, Object> saveSubmission(Map<String, Object> data) {

		Map<String, Object> responseObject = new HashMap<String, Object>();
		// Omitting 0 from mobile numbers starting with 0
		String mob = "";
		if (String.valueOf(data.get("mobile")).startsWith("0")) {
			mob = String.valueOf(data.get("mobile")).replaceFirst("0", "");
			data.put("mobile", mob);
		}

		// Checking for duplicate mobile number
		SubmissionData oldData = submissionRepository.getSubmissionByMobileNumber(String.valueOf(data.get("mobile")));
		if (oldData != null && oldData.getData().get("mobile").equals(data.get("mobile"))) {
			responseObject.put("status", "duplicate_mobile_number");
			responseObject.put("message", "Mobile number already linked with another account");

			return responseObject;
		}
		SubmissionData submission = new SubmissionData();
		submission.setFormId(Integer.parseInt(String.valueOf(data.get("formId"))));
		submission.setCreatedBy(String.valueOf(data.get("email")));
		submission.setCreatedDate(new Date());
		submission.setIsVerified(false);
		submission.setData(data);
		SubmissionData s = null;
		try {
			s = submissionRepository.save(submission);
			responseObject.put("id", s.get_id());
			if (s.get_id() != null) {
				responseObject.put("status", "success");
				try {
					sendVerificationMessage(data);
				} catch (Exception e) {
					e.printStackTrace();
					responseObject.put("status", "mobile_number_error");
					responseObject.put("message", "Mobile number doesn't exists");
				}
			} else {
				responseObject.put("status", "failed");
			}
		} catch (Exception e) {
			responseObject.put("status", "failed");
			log.error("Action : while submitting new data with payload {}", data, e);
		}
		return responseObject;
	}

	// Sends OTP after registration to associated mobile number
	public Integer sendVerificationMessage(Map<String, Object> data) {
		RestTemplate restTemplate = new RestTemplate();
		int status = 0;

		// Configuration
		String url = configurableEnvironment.getProperty("msg.url.and.credential");
		String msgType = configurableEnvironment.getProperty("msg.type");
		String callBackURL = configurableEnvironment.getProperty("callback.url");
		String senderId = configurableEnvironment.getProperty("sender.id");
		String password = configurableEnvironment.getProperty("password");
		String version = configurableEnvironment.getProperty("version");

		// Checking for available OTP
		OTPToken existingToken = otpRepository.findByMobileNumberAndIsValid(String.valueOf(data.get("mobile")), true);
		String otp = existingToken != null ? existingToken.getOtp() : OTP(6);
		String message = "Dear volunteer, please use OTP " + otp + " to complete COVID Odisha volunteer registration. "
				+ "OTP is valid for 30 mins.";
		String urlMSG = url + password + version + data.get("mobile") + msgType + message + senderId + callBackURL;
		ResponseEntity<String> entity = null;
		try {
			entity = restTemplate.exchange(urlMSG, HttpMethod.GET, null, String.class);
			JsonObject responseJson = new Gson().fromJson(entity.getBody(), JsonObject.class);
			status = responseJson.getAsJsonObject("result").getAsJsonObject("status").get("statusCode").getAsInt();

			if (status == 0 && existingToken == null) {
				OTPToken otpToken = new OTPToken(otp, new Date(), String.valueOf(data.get("mobile")), true);
				otpRepository.save(otpToken);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	// Validates if the provided OTP is valid or not
	public Map<String, Object> validateOTP(String otp, String mobileNumber, String id) {
		Map<String, Object> resp = new HashMap<>();
		SubmissionData data = submissionRepository.findBy_id(id);
		OTPToken otpToken = otpRepository.getOtp(mobileNumber, otp);
		if (otpToken != null && data.getData().get("mobile").equals(mobileNumber)) {
			data.setIsVerified(true);
			submissionRepository.save(data);
			otpToken.setValid(false);
			otpRepository.save(otpToken);
			resp.put("isValid", true);
			sendConfirmationMessage(data);
		}
		return resp;
	}

	// Generating OTP
	static String OTP(int len) {
		// Using numeric values
		String numbers = "0123456789";

		// Using random method
		Random rndm_method = new Random();

		char[] otp = new char[len];
		String otpStr = "";
		for (int i = 0; i < len; i++) {
			// Use of charAt() method : to get character value
			// Use of nextInt() as it is scanning the value as int
			otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
			otpStr = otpStr + otp[i];
		}
		return otpStr;
	}

	// Returns the list of volunteers to render in table
	@Override
	public List<Map<String, Object>> getListOfVolunteer() {
		// Fetching user account details
		Account acc = customAccountRepository
				.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		UserDetails user = acc != null ? (UserDetails) acc.getUserDetails() : null;
		String areaLevel = "data.preferredDistrict";
		Integer areaId = 13;
		if (user != null && user.getAreaId().contains(2)) {
			areaLevel = "data.state";
			areaId = user.getAreaId().get(0);
		} else if (user != null) {
			areaLevel = "data.preferredDistrict";
			areaId = user.getAreaId().get(0);
		}
		List<SubmissionData> volunteers = submissionRepository.getVolunteersListForTaskComponent(areaLevel, areaId);
		List<SubmissionData> organizations = submissionRepository.getOrganizationsForTaskComponent(areaLevel, areaId);
		List<Map<String, Object>> resp_object = new ArrayList<>();
		// Creating table object for volunteer submissions
		volunteers.forEach(v -> {
			Map<String, Object> volunteer = new HashMap<>();
			String service = "";
			service = v.getData().get("awareness") != null ? service + "Public awareness and Information," : service;
			service = v.getData().get("fieldSupport") != null ? service + "On-field support," : service;
			service = v.getData().get("essentials") != null ? service + "Essential services," : service;
			service = v.getData().get("health") != null ? service + "Health," : service;
			service = v.getData().get("reliefCampMgmt") != null ? service + "Relief Camp Management," : service;

			volunteer.put("Name", String.valueOf(v.getData().get("fullName")));
			volunteer.put("Mobile", String.valueOf(v.getData().get("mobile")));
			volunteer.put("Preferred Activity",
					service.length() > 1 ? service.substring(0, service.length() - 1) : service);
			volunteer.put("formId", v.getFormId());
			volunteer.put("isAssignedTask", v.getIsAssignedTask());
			volunteer.put("deactivated",
					v.getData().get("deactivated") != null
							? Boolean.parseBoolean(String.valueOf(v.getData().get("deactivated")))
							: false);

			resp_object.add(volunteer);
		});

		// Creating table object for organization submissions
		organizations.forEach(org -> {
			Map<String, Object> organization = new HashMap<>();
			String service = "";
			service = typeDetailRepository
					.findBySlugId(Integer.parseInt(String.valueOf(org.getData().get("serviceType")))).getName();
			service = service + " : " + String.valueOf(org.getData().get("serviceDetails"));

			organization.put("Name", String.valueOf(org.getData().get("organizationName")));
			organization.put("Mobile", String.valueOf(org.getData().get("mobile")));
			organization.put("Preferred Activity", service);
			organization.put("formId", org.getFormId());
			organization.put("isAssignedTask", org.getIsAssignedTask());
			organization.put("deactivated",
					org.getData().get("deactivated") != null
							? Boolean.parseBoolean(String.valueOf(org.getData().get("deactivated")))
							: false);

			resp_object.add(organization);
		});
		return resp_object;
	}

	// Returns a specific volunteer given the mobile number
	@Override
	public Map<String, Object> getVolunteerDetails(String mobile) {
		SubmissionData volunteerDetails = submissionRepository.getSubmissionByMobileNumber(mobile);
		Map<String, Object> data = volunteerDetails.getData();
		// Fetching the profile image of volunteer
		if (volunteerDetails.getFormId() == 1 && volunteerDetails != null) {
			if (!StringUtils.isEmpty((String) data.get("profilePic"))) {
				data.put("profilePic", ImageConverter
						.encodingPhoto(photoIdfilepath + "profilepics/" + (String) data.get("profilePic")));
			}
			volunteerDetails.setData(data);
		}

		return volunteerDetails != null ? volunteerDetails.getData() : null;
	}

	// Checks and returns if a mobile number is already registered or not
	@Override
	public Map<String, String> getRegistrationStatus(String mobile,Integer languageId){
		SubmissionData oldData = submissionRepository.getSubmissionByMobileNumber(mobile);
		Map<String, String> resp = new HashMap<>(); 
		int lang=0;
		if (oldData != null) {
			resp.put("status", "active");
			if(languageId==2)
				resp.put("message", "ଏହି ମୋବାଇଲ୍ ନମ୍ବର ପୂର୍ବରୁ ପଞ୍ଜିକୃତ ହୋଇଛି | ଅଧିକ ସୂଚନା ପାଇଁ, ଦୟାକରି ହେଲ୍ପ ଡେସ୍କ ସହିତ ଯୋଗାଯୋଗ କରନ୍ତୁ |");
			else	
				resp.put("message", "This number is already registered. Should you require any further information, please contact the help desk.");
		} else {
			resp.put("status", "notactive");
			if(languageId==2)
				resp.put("message", "ଏହି ମୋବାଇଲ୍ ନମ୍ବର ପୋର୍ଟାଲରେ ପଞ୍ଜୀକୃତ ହୋଇନାହିଁ |");
			else	
				resp.put("message", "This number is not registered in the portal.");
		}
		return resp;
	}

	private Integer sendConfirmationMessage(SubmissionData submissionData) {
		Map<String, Object> data = submissionData.getData();
		RestTemplate restTemplate = new RestTemplate();
		int status = 0;
		String url = configurableEnvironment.getProperty("msg.url.and.credential");
		String msgType = configurableEnvironment.getProperty("msg.type");
		String callBackURL = configurableEnvironment.getProperty("callback.url");
		String senderId = configurableEnvironment.getProperty("sender.id");
		String password = configurableEnvironment.getProperty("password");
		String version = configurableEnvironment.getProperty("version");

		String message = "You have been successfully registered on the Covid Sangramee Portal.";
		String urlMSG = url + password + version + data.get("mobile") + msgType + message + senderId + callBackURL;
		ResponseEntity<String> entity = null;
		try {
			entity = restTemplate.exchange(urlMSG, HttpMethod.GET, null, String.class);
			JsonObject responseJson = new Gson().fromJson(entity.getBody(), JsonObject.class);
			status = responseJson.getAsJsonObject("result").getAsJsonObject("status").get("statusCode").getAsInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
}
