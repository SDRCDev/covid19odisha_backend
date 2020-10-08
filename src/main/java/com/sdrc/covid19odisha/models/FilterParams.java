package com.sdrc.covid19odisha.models;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class FilterParams {
	private Integer areaLevel;
	private List<Integer> districts; 
	private List<Integer> blocks; 
	private List<Integer> gps;
	private List<Integer> genders; 
	private List<Integer> qualifications; 
	private List<Integer> vocationals;
	private List<Integer> professions;
	private List<Integer> availability;
	private List<Integer> workFrom; 
	private List<Integer> workTime; 
	private List<Integer> awareness;
	private List<Integer> fieldSupport;
	private List<Integer> essentials;
	private List<Integer> health;
	private List<Integer> reliefCampMgmt;
	private Date fromDate;
	private Date toDate;
	private Boolean isAssignedTask;
	private Integer organizationType;
	private Integer serviceType;
	private Map<String,List<String>> questionMap;
}
