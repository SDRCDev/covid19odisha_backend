package com.sdrc.covid19odisha.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.sdrc.covid19odisha.models.FilterParams;

public interface TaskService {
	public void saveTask(Map<String, Object> data);

	public Map<String, List<?>> getTaskList(String mobile);

	Map<String, List<?>> getFilteredVolunteers(FilterParams params);

	Map<String, List<?>> getFilteredOrganizations(FilterParams params);

	public ResponseEntity<String> generateCertificate(String mobile, OAuth2Authentication oauth);

	public void deactivateVolunteer(Map<String, Object> data);

}