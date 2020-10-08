package com.sdrc.covid19odisha.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sdrc.covid19odisha.models.FilterParams;
import com.sdrc.covid19odisha.services.TaskService;

@RestController
public class TaskAssignmentController {
	
	@Autowired
	private TaskService taskService;
	
	// API to add task for individual volunteer
	@PostMapping("/addTask")
	public Map<String, Object> saveTask(@RequestBody Map<String, Object> data) {
		taskService.saveTask(data);
		data.put("status", "success");
		return data;
	}
	
	// API to add task for multiple volunteers
	@PostMapping("/addBulkTask")
	public List<Map<String, Object>> saveBulkTask(@RequestBody List<Map<String, Object>> datas) {
		for(Map<String, Object> data:datas) {
			taskService.saveTask(data);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("status", "success");
		datas.add(data);
		return datas;
	}
	
	// This API fetches all the tasks assigned to a particular volunteer
	@GetMapping("/getTaskList")
	public Map<String, List<?>> getTaskList(@RequestParam("mobile") String mobile){
		return taskService.getTaskList(mobile);
	}
	
	// After applying filters to the volunteer list in task management this API get called and returns filtered list of volunteers
	@PostMapping("/getFilteredVolunteers")
	public Map<String, List<?>> getFilteredVolunteers(@RequestBody FilterParams params)  throws Exception{
		return taskService.getFilteredVolunteers(params);
	}
	
	// After applying filters to the organization list in task management this API get called and returns filtered list of organizations
	@PostMapping("/getFilteredOrganizations")
	public Map<String, List<?>> getFilteredOrganizations(@RequestBody FilterParams params)  throws Exception{
		return taskService.getFilteredOrganizations(params);
	}
	
	// Deactivates a volunteer
	@PostMapping("/deactivateVolunteer")
	public String deactivateVolunteer(@RequestBody Map<String, Object> data){
		taskService.deactivateVolunteer(data);
		return "deactivated";
	}
	
	// Generates and downloads certificate for a particular volunteer
	@GetMapping("/generateCertificate")
	public ResponseEntity<String> generateCertificate(@RequestParam("mobile") String mobile, OAuth2Authentication oauth){
		return taskService.generateCertificate(mobile, oauth);
	}
}
