package com.sdrc.covid19odisha.controllers;

import java.util.List;

import org.sdrc.usermgmt.mongodb.domain.Designation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sdrc.covid19odisha.models.AccountTableJSONModel;
import com.sdrc.covid19odisha.models.RejectionModel;
import com.sdrc.covid19odisha.models.UserApprovalModel;
import com.sdrc.covid19odisha.services.UserRegistrationService;

@RestController
public class UserRegistrationController {
	
	@Autowired
	private UserRegistrationService userRegistrationService;
	
	@GetMapping("/getAllRoles")
	public List<Designation> getAllRoles(){
		return userRegistrationService.getAllRoles();
	}
	
	
	@GetMapping(value = "/getEmailVarificationCode")
	 public  ResponseEntity<String> getEmailVarificationCode(
				@RequestParam("email") String email) {
			return userRegistrationService.getEmailVerificationCode(email);
		}
	 
	 @GetMapping(value = "/getEmailOTPAvailability")
	public ResponseEntity<String> OTPAndEmailAvailibility(@RequestParam("email") String email,
			@RequestParam("varificationCode") Integer varificationCode) {
		return userRegistrationService.oTPAndEmailAvailibility(email, varificationCode);
	}
	 
	 @GetMapping("/getAllUser")
		public AccountTableJSONModel getPartnersForApproval() {
			return userRegistrationService.getPartnersForApproval();
		}
	 

		@GetMapping("/approveUser")
		public ResponseEntity<String> approvePartner(@RequestParam("ids") List<String> ids) {
			return userRegistrationService.approvePartner(ids);
		}

		@PostMapping("/rejectUser")
		public ResponseEntity<String> rejectPartner(@RequestParam("ids") List<String> ids,@RequestBody List<RejectionModel> rejectionModel) {
			return userRegistrationService.rejectPartner(ids,rejectionModel);
		}
		
		@GetMapping("/getUsersByRole")
		public List<UserApprovalModel> getUsersByRoleAndPartner(@RequestParam("roleId") List<String> roleIds){
			return userRegistrationService.getUsersByRoleAndPartner(roleIds);
		}

}
