package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TodoItem;
import com.example.demo.repository.TodoItemRepository;

import jakarta.transaction.Transactional;

@Service
public class TodoItemServiceAdd {

    @Autowired
    TodoItemRepository repository;
    @Autowired
    TodoItemServiceAddValidator validator;
    
    @Transactional
    //todoを追加
    public String createNewTodoItem(TodoItem item, boolean isDone) {
        String validationResult = validator.validateAddTodoItem(item);
        if (validationResult != null) {
            return validationResult;
        }
        // バリデーションに合格した場合の処理
        item.setDone(false);
        repository.save(item);
        return null;
    }
    
    //getTodoItemsでisDoneの内容を降順で表示する
    public List<TodoItem> getTodoItems(boolean isDone) {
    	return repository.findByDoneOrderByPriorityDesc(isDone);
    }
}