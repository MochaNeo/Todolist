package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TodoItem;
import com.example.demo.repository.TodoItemRepository;

import jakarta.transaction.Transactional;

//テーブルの更新
@Service
public class TodoItemServiceStatus {
	
	@Autowired
	TodoItemRepository repository;
	
	//todoステータスの変更
    public void updateDoneStatus(long id, boolean done) {
    	Optional<TodoItem> itemOptional = repository.findById(id);
    	if (itemOptional.isPresent()) {
    		TodoItem item = itemOptional.get();
    		item.setDone(done);
    		repository.save(item);
    	}
    }
    
    //todoの削除
    @Transactional
	public void deleteTodo(long id) {
		Optional<TodoItem> itemOptional = this.repository.findById(id);
        if (itemOptional.isPresent()) {
            TodoItem item = itemOptional.get();
            this.repository.delete(item);         
        }
    }
}