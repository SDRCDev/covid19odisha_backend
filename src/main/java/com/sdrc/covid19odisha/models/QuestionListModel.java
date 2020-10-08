package com.sdrc.covid19odisha.models;

import java.util.List;

import lombok.Data;

@Data
public class QuestionListModel {

	private String name;
	private boolean value;
	private List<QuestionListModel> child;
}
