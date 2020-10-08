package com.sdrc.covid19odisha.models;

import lombok.Data;

@Data
public class UserApprovalModel {

	private String id;

	private String userName;

	private String name;

	private String dob;

	private String gender;

	private String mobileNumber;

	private String organization;

	private String designation;

	private String submittedOn;

	private String email;

	private String status;
	
	private Boolean isActive;
	
	private String partnerName;
	
	private String areaName;
	
	private int slNo;

}
