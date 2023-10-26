package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TodoItem;
import com.example.demo.form.TodoItemForm;
import com.example.demo.repository.TodoRepository;

import jakarta.transaction.Transactional;

@Service
public class TodoAddService {
	
	
    @Autowired
    TodoRepository repository;
    
    
    //todoを追加
    @Transactional
    public String createNewTodoItem(TodoItemForm todoItemForm, TodoItem item, String validationResult) {
        if (validationResult != null) {
        	//バリデーションにてエラーが発生した場合の処理
        	todoItemForm.setErrorMessage(validationResult);
        	todoItemForm.setTodoItems(repository.findByDoneOrderByPriorityDesc(todoItemForm.isDone()));
            return validationResult;
        }	// バリデーションに合格した場合の処理
        	item.setDone(false);
            repository.save(item);
            return null;
    }
}