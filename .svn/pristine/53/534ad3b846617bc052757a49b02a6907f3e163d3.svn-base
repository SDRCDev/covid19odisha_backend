package com.sdrc.covid19odisha.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sdrc.covid19odisha.collections.Area;
import com.sdrc.covid19odisha.collections.TypeDetail;
import com.sdrc.covid19odisha.services.MasterDataApiService;
import com.sdrc.covid19odisha.services.SubmissionService;

@RestController
//@CrossOrigin(origins = {"https://testserver.sdrc.co.in:8443", "http://prod1.sdrc.co.in/"})
public class MasterDataController {

	@Autowired
	private MasterDataApiService masterService;
	
	@Autowired
	private SubmissionService submissionService;
	
	//API call for fetching area list 
	@RequestMapping(value="/area")
	public List<Area> getAreaList(@RequestParam("areaLevelId") String areaLevelId, 
			@RequestParam("parentAreaId") String parentAreaId){
		return masterService.getAreaList(Integer.parseInt(areaLevelId), Integer.parseInt(parentAreaId));
	}
	
	@RequestMapping(value="/multiArea")
	public List<Area> getMultipleParestAreaList(@RequestParam("areaLevelId") String areaLevelId, 
			@RequestParam("parentAreaId") Integer parentAreaId){
		return masterService.getAreaList(Integer.parseInt(areaLevelId), parentAreaId);
	};
	
	//API call for fetching options
	@GetMapping("/options")
	public Map<String, List<TypeDetail>> getOptions() {
		return masterService.getOptions();
	}
	
	@PostMapping("/updateOption")
	public String updateOption(@RequestBody Map<String, Object> option) {
		masterService.updateOption(option);
		return "success";
	}
}
