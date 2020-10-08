package com.sdrc.covid19odisha.collections;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class LanguagePreference {

	private String id;
	private String ip;
	private String language;
	private Integer languageId;
}
