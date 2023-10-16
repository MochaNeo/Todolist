package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TodoItem;
import com.example.demo.repository.TodoItemRepository;

import jakarta.transaction.Transactional;

@Service
public class TodoItemServiceDoneStatus {
	
	@Autowired
    TodoItemRepository repository;
    
	//完了したアイテム、未完了のアイテムを表示
    @Transactional
    public void updateDoneStatus(long id, boolean done) {
    	Optional<TodoItem> itemOptional = repository.findById(id);
    	if (itemOptional.isPresent()) {
    		TodoItem item = itemOptional.get();
    		item.setDone(done);
    		repository.save(item);
    	}
    }
}