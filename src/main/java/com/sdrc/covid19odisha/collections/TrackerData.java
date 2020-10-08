package com.sdrc.covid19odisha.collections;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class TrackerData {
	
	@Id
	private String id; 
	/*private Integer sbmAreaId;
	private Integer confirmedCases;
	private Integer confirmedCasesDiff;
	private Integer activeCases;
	private Integer activeCasesDiff;
	private Integer recoveredCases;
	private Integer recoveredCasesDiff;
	private Integer deceasedCases;
	private Integer deceasedCasesDiff;*/
	List<Map<String,Object>> data;
	private String dbDate;
	private Date createdDate;
	private Date updatedDate;

}
