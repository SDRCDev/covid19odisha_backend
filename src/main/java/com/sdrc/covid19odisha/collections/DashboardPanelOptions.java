package com.sdrc.covid19odisha.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class DashboardPanelOptions {
	@Id
	public String _id;
	public Integer slugId;
	public String name;
	public Integer formId;
	public String path;
	public Double value;
	public Integer typeDetailId;
	public String aggregatePath;
}
