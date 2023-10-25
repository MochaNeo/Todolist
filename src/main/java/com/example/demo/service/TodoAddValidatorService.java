package com.example.demo.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.demo.entity.TodoItem;

@Service
public class TodoAddValidatorService {
	
	//追加するtodoのバリデーション
	public String validateAddTodoItem(TodoItem item) {
	    final String title = item.getTitle();
	    final Date dueDate = item.getDueDate();
	    final Date yesterday = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
	    
	    final String titleNullError = "タイトルを入力してください";
	    final String titleBlankError = "タイトルに空白が含まれています";
	    final String[] ngWord = {};
	    final String ngWordError = "NGワードに設定されている文字が含まれます。";
	    final String dueDateError = "期日が昨日以前の日付です。有効な日付を設定してください";
	    String errorMessage = "";
	    
	    
	    if (title.equals("")) {
			errorMessage += titleNullError + "\n";
		} else if (title.contains(" ")) {
		    errorMessage += titleBlankError + "\n";
		} else if (title.contains("　")) {
			errorMessage += titleBlankError + "\n";
		}
	    for (String ngWords : ngWord) {
			if (title.trim().toLowerCase().contains(ngWords)) {
				errorMessage += ngWordError + "\n";
				break;
			}
		}
		if (dueDate.before(yesterday)) {
			errorMessage += dueDateError + "\n";
		}
		return errorMessage.isEmpty() ? null : errorMessage;
	}
}