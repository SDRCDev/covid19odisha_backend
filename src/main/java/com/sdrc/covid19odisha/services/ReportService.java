package com.sdrc.covid19odisha.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.sdrc.covid19odisha.collections.SubmissionData;
import com.sdrc.covid19odisha.models.FilterParams;

public interface ReportService {

	Map<String, List<?>> getQueryReportData();
	
	ResponseEntity<String> getFilteredVolunteers(FilterParams params)  throws Exception;

	ResponseEntity<String> getFilteredOrganizations(FilterParams params) throws Exception;

}
