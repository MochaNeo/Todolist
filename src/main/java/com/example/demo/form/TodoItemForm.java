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
    //入力された値を管理するフィールド
    private String title;
    private int category;
    private int priority;
    //boolean型のisDone（）とtodoitemsのリストを作成
    //isDoneは完了か未完了か
}