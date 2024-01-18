package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TodoItem;
import com.example.demo.repository.TodoRepository;

import jakarta.transaction.Transactional;

//テーブルの更新
@Service
public class TodoStatusService {
	
	@Autowired
	TodoRepository repository;
	
	//todoステータスの変更
	@Transactional
	public void updateStatus(long id, boolean done) {
	    TodoItem item = repository.findById(id).get();
	    item.setProgress(done);
	    repository.save(item);
	}
	
    //todoの削除
    @Transactional
	public void deleteTodo(long id) {
    	repository.deleteById(id);
    }
    
    //todoをすべて削除
    @Transactional
    public void allDeleteTodo() {
        repository.deleteAll(repository.findByProgressOrderByPriorityDesc(true));//itemsToDeleteのレコードをすべて削除
    }

}