package com.example.demo.form;

import java.util.List;

import com.example.demo.entity.TodoItem;

import lombok.Getter;
import lombok.Setter;

//Thymeleafテンプレートのビューとコントローラーの間でデータを受け渡すのに使用される
@Getter
@Setter
public class TodoItemForm {
    private boolean isDone;
    private String errorMessage;
    private List<TodoItem> todoItems;
    private String title;
    private String detail = "";
    private int category = 0;
    private int priority = 0;
}