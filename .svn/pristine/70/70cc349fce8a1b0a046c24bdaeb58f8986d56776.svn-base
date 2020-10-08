package com.sdrc.covid19odisha.services;

import java.util.List;

import org.sdrc.usermgmt.mongodb.domain.Designation;
import org.springframework.http.ResponseEntity;

import com.sdrc.covid19odisha.models.AccountTableJSONModel;
import com.sdrc.covid19odisha.models.RejectionModel;
import com.sdrc.covid19odisha.models.UserApprovalModel;

public interface UserRegistrationService {
	
	List<Designation> getAllRoles();

	ResponseEntity<String> getEmailVerificationCode(String email);

	ResponseEntity<String> oTPAndEmailAvailibility(String email, Integer varificationCode);

	AccountTableJSONModel getPartnersForApproval();

	ResponseEntity<String> approvePartner(List<String> ids);

	ResponseEntity<String> rejectPartner(List<String> ids, List<RejectionModel> rejectionModel);

	List<UserApprovalModel> getUsersByRoleAndPartner(List<String> roleIds);

}
