package com.example.demo.form;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
//検索条件を格納する
public class TodoSearchCriteria {
	private String title;
	private int category;
	private int priority;
	private boolean done;
	
	public void setCriteria(Map<String, Object> condition) {
		setTitle((String)condition.get("title"));
		setCategory((int)condition.get("category"));
		setPriority((int)condition.get("priority"));
		setDone((boolean)condition.get("done"));
	}
}