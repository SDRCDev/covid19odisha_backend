package com.sdrc.covid19odisha.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdrc.covid19odisha.services.ConfigurationService;

/**
 * @author Debiprasad Parida (debiprasad@sdrc.co.in)
 */

@RestController
@RequestMapping("/api")
public class ConfigurationController {

	@Autowired
	private ConfigurationService configurationService;


	@GetMapping("/mongoClient")
	public String createMongoOauth2Client() {

		return configurationService.createMongoOauth2Client();

	}
	

	@GetMapping("/config")
	public ResponseEntity<String> config() {
		return configurationService.config();
	}

	
}
