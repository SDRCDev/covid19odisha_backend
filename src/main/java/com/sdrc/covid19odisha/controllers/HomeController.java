package com.sdrc.covid19odisha.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sdrc.covid19odisha.collections.TrackerData;
import com.sdrc.covid19odisha.models.QuestionListModel;
import com.sdrc.covid19odisha.services.HomeService;

@RestController
public class HomeController {
	
	@Autowired
	private HomeService homeService;
	
	@GetMapping("/getIndicatorValue")
	public ResponseEntity<Map<String,Integer>> getIndicatorValue() {
		return homeService.getIndicatorValue();
	}
	
	@PostMapping("/saveTabularData")
	public ResponseEntity<String> saveTabularData(@RequestBody List<Map<String,Object>> data,@RequestParam(value = "date", required = false) String date){
	return homeService.saveTabularData(data,date);
	}
	
	@GetMapping("/getTabularData")
	public ResponseEntity<TrackerData> getTabularData(@RequestParam(value = "date", required = false) String date){
	return homeService.getTabularData(date);
	}
	
	@PostMapping("/getRawDataReport")
	ResponseEntity<String> getRawData(@RequestParam(value = "formId", required = false) Integer formId,
			@RequestParam(value = "sdate", required = false) String sdate,
			@RequestParam(value = "edate", required = false) String edate, OAuth2Authentication oauth,
			@RequestBody  Map<String,List<String>> questionMap) throws Exception { 
		return homeService.getRawData(formId, sdate, edate,oauth,questionMap);
	}
	
	@RequestMapping(value = "/downloadReport", method = RequestMethod.POST)
	public void downLoad(@RequestParam("fileName") String name, HttpServletResponse response) throws IOException {

		InputStream inputStream;
		try {
			String fileName = name.replaceAll("%3A", ":").replaceAll("%2F", "/").replaceAll("%2C", ",")
					.replaceAll("\\+", " ").replaceAll("%20", " ").replaceAll("%26", "&").replaceAll("%5C", "/");
			inputStream = new FileInputStream(fileName);
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", new java.io.File(fileName).getName());
			response.setHeader(headerKey, headerValue);
			response.setContentType("application/octet-stream"); // for all file
																	// type
			ServletOutputStream outputStream = response.getOutputStream();
			FileCopyUtils.copy(inputStream, outputStream);
			inputStream.close();
			outputStream.flush();
			outputStream.close();
			new File(fileName).delete();

		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	
	@GetMapping("/getReportCount")
	public ResponseEntity<Map<String,Integer>> getReportCount( OAuth2Authentication oauth) {
		return homeService.getReportCount(oauth);
	}
	
	@GetMapping("/resetTabularData")
	public ResponseEntity<TrackerData> resetTabularData(@RequestParam(value = "date") String date){
	return homeService.resetTabularData(date);
	}
	
	
	@GetMapping(value="/bypass/quickStartData")
	public ResponseEntity<String> quickStartData(HttpServletRequest request) throws Exception {
		return homeService.quickStartData(request);
	}
	
	@GetMapping("/bypass/fetchVistorCount")
	public long  fetchVistorCount() {
		return homeService.getVisitorCount();
	}
	
	
	@GetMapping("/getQuestionDropDownList")
	ResponseEntity<List<QuestionListModel>> getQuestionDropDown(@RequestParam(value = "formId") Integer formId) throws Exception { 
		return homeService.getQuestionDropDown(formId);
	}
}
