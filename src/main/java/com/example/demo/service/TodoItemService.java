package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TodoItem;
import com.example.demo.repository.TodoItemDaoImpl;
import com.example.demo.repository.TodoItemRepository;

@Service
public class TodoItemService {
	
	
    @Autowired
    TodoItemRepository todoItemRepository;
    @Autowired
    TodoItemDaoImpl todoItemDaoImpl;
    
    
    //全体検索
    public Optional<TodoItem> findById(long id) {
    	return todoItemRepository.findById(id);
    }
    //保存
    public TodoItem save(TodoItem todoItem) {
    	return todoItemRepository.saveAndFlush(todoItem);
    }
    //検索
    public List<TodoItem> search(String title, String category, String priority) {
    	List<TodoItem> result = new ArrayList<TodoItem>();
    	
    	if("".equals(title) && "".equals(category) && "".equals(priority)) {
    		result = todoItemRepository.findAll();
    	} else {
    		result = todoItemDaoImpl.search(title, category, priority);
    	}
    	return result;
    }
    
}