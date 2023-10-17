package com.example.demo.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.demo.entity.TodoItem;

@Service
public class TodoItemServiceAddValidator {
	
	//追加するtodoのバリデーション
	public String validateAddTodoItem(TodoItem item) {
	    String title = item.getTitle();
	    Date dueDate = item.getDueDate();
	    Date yesterday = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
	    
	    
	    String[] ngWords = {"", " ", "　"}; // NGワードを配列で定義
	    String titleNullError = "タイトルを入力してください";
	    String titleBlankError = "タイトルに空白が含まれています";
	    String dueDateError = "期日が昨日以前の日付です。有効な日付を設定してください";
	    
	    
	    
	    for (String ngWord : ngWords) {
	    	if (title.equals("")) {
	    		return titleNullError;
	    	}
	    	if (title.contains(" ")) {
	    		return titleBlankError;
	    	}
	    	if (title.contains("　")) {
	    		return titleBlankError;
	    	}
	    	if (title.trim().toLowerCase().contains(ngWord)) {
	    		return "NGワード「" + ngWord + "」は設定できません";
	        }
	    }
	    if (dueDate.before(yesterday)) {
	        return dueDateError;
	    }
	    return null;
	}
}