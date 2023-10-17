package com.example.demo.form;

import java.util.List;

import com.example.demo.entity.TodoItem;

import lombok.Getter;
import lombok.Setter;

//フォームの内容を管理する
@Getter
@Setter
public class TodoItemForm {
    private boolean isDone;
    private String errorMessage;
    private List<TodoItem> todoItems;
    private String title;
    private int category;
    private int priority;
}