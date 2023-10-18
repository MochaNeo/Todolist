package com.example.demo.service;

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
	@Transactional
	public void updateStatus(long id, boolean done) {
	    TodoItem item = repository.findById(id).get();
	    item.setDone(done);
	    repository.save(item);
	}
	
    //todoの削除
    @Transactional
	public void deleteTodo(long id) {
    	repository.deleteById(id);
    }
}