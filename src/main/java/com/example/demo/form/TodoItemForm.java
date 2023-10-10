package com.example.demo.form;

import java.util.List;

import com.example.demo.entity.TodoItem;

import lombok.Getter;
import lombok.Setter;

//controllerからThymeleafにデータを渡すクラス
//TodoItemと分けるのはデータベースに関係ない表示処理とかを定義するため
@Getter
@Setter
public class TodoItemForm {
    private boolean isDone;
    private String errorMessage;
    private List<TodoItem> todoItems;
    
    //boolean型のisDone（）とtodoitemsのリストを作成
    //isDoneは完了か未完了か
}