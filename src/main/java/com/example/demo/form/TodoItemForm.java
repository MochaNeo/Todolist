package com.example.demo.form;

import java.util.List;

import com.example.demo.entity.TodoItem;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

//getリクエスト時にはnewされたインスタンスが作成され、postリクエスト時にはフォームの値がフィールドに代入される
//またindexを表示した際にはテーブルにはtodoitemsとdoneitemsが表示される
@Getter
@Setter
public class TodoItemForm {
    private boolean progress;
    private List<TodoItem> todoItems;
    private List<TodoItem> doneItems;


    @Column(nullable = false)
    @NotBlank(message = "タイトルは必須です")
    @NotNull(message = "タイトルは必須です")
    private String title = "";
    private int category = 0;
    private String description = "";
    private int priority = 0;
    //searchメソッドで使用する
    //未入力の場合にはデフォルトの値が使用できるようにする
    private String searchTitle = "";
    private int searchCategory = 0;
    private int searchPriority = 0;
}