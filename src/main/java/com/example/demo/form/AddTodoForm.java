package com.example.demo.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddTodoForm {
	private String title;
    private String category;
    private String priority;
}