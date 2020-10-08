package com.sdrc.covid19odisha.models;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class CmsViewModel {
	
	private List<String> tableColumn;
	
	private Map<String, Object> tableData;

	

}
