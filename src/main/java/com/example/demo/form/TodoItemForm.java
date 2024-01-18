package com.example.demo.form;

import java.util.List;

import com.example.demo.entity.TodoItem;

import lombok.Getter;
import lombok.Setter;

//getリクエスト時にはnewされたインスタンスが作成され、postリクエスト時にはフォームの値がフィールドに代入される
//またindexを表示した際にはテーブルにはtodoitemsとdoneitemsが表示される
@Getter
@Setter
public class TodoItemForm {
    private boolean progress;
    private String errorMessage;
    private List<TodoItem> todoItems;
    private List<TodoItem> doneItems;
    private String title = "";
    private String description = "";
    private int category = 0;
    private int priority = 0;
    //searchメソッドで使用する
    //未入力の場合にはデフォルトの値が使用できるようにする
    private String searchTitle = "";
    private int searchCategory = 0;
    private int searchPriority = 0;
}