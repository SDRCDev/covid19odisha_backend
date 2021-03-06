package com.sdrc.covid19odisha.collections;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Task {
	@Id
	private String _id;
	
	private String userId;
	
	private String mobile;
	
	private Date createdDate;
	
	private Map<String, Object> data;
}
