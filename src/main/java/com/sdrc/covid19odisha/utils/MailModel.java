package com.sdrc.covid19odisha.utils;

import java.util.List;
import java.util.Map;

import lombok.Data;
@Data
public class MailModel {

	private String fromUserName;

	private String toUserName;

	private List<String> toEmailIds;

	private List<String> ccEmailIds;

	private String subject;

	private String message;

	private Map<String, String> attachments;
	
	private String email;

}
