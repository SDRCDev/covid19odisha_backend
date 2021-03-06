package com.sdrc.covid19odisha.services;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.sdrc.covid19odisha.collections.TrackerData;
import com.sdrc.covid19odisha.models.QuestionListModel;

public interface HomeService {

	ResponseEntity<Map<String, Integer>> getIndicatorValue();

	ResponseEntity<String> saveTabularData(List<Map<String,Object>> data,String date);
	
	void cornJob();

	ResponseEntity<TrackerData> getTabularData(String date);

	ResponseEntity<String> getRawData(Integer formId, String sdate, String edate, OAuth2Authentication oauth,Map<String,List<String>> qstMap) throws Exception ;
	
	ResponseEntity<TrackerData> resetTabularData(String date);
	
	ResponseEntity<Map<String, Integer>> getReportCount( OAuth2Authentication oauth);

	long getVisitorCount();

	ResponseEntity<String> quickStartData(HttpServletRequest request);
	
	ResponseEntity<List<QuestionListModel>> getQuestionDropDown(Integer formId);

}
