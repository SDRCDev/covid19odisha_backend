package com.sdrc.covid19odisha.collections;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class OTPToken {
	@Id
	private String _id;
	private String otp;
	private Date createdTime;
	private String mobileNumber;
	private boolean isValid;
	
	public OTPToken(String otp, Date createdTime, String mobileNumber, boolean isValid) {
		super();
		this.otp = otp;
		this.createdTime = createdTime;
		this.mobileNumber = mobileNumber;
		this.isValid = isValid;
	}
	
}
