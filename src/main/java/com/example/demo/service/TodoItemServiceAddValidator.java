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
    	
    		if (title.equals("")) {
    			return "タイトルを入力してください。";
    		} else if (title.equals(" ")) {
    		    return "タイトルに空白が含まれています";
    		} else if (title.trim().toLowerCase().contains("宿題")) {
    			return "NGワード「宿題」は設定できません。";
    		} else if (dueDate.before(yesterday)) {
    			return "期日が昨日以前の日付です。有効な日付を設定してください。";
    		}
    	return null;
    }
}

