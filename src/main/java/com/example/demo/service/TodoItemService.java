package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TodoItem;
import com.example.demo.repository.TodoItemDaoImpl;
import com.example.demo.repository.TodoItemRepository;

//コントローラーからの指示を受け、必要に応じてRepositoryを介しながら処理を行う。
@Service
public class TodoItemService {
	
    @Autowired
    TodoItemRepository todoItemRepository;
    @Autowired
    TodoItemDaoImpl todoItemDaoImpl;
    
    //検索
    public List<TodoItem> search(String title, String category, String priority, boolean done) {
    	List<TodoItem> result = new ArrayList<TodoItem>();
    	
    	if("".equals(title) && "".equals(category) && "".equals(priority) && "".equals(done)) {
    		result = todoItemRepository.findAll();
    	} else {
    		result = todoItemDaoImpl.search(title, category, priority, done);
    	}
    	return result;
    }
}