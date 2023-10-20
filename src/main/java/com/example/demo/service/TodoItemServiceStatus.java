package com.example.demo.service;

import java.util.List;

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
    
    
    //todoをすべて削除
    @Transactional
    public void allDeleteTodo() {
        List<TodoItem> itemsToDelete = repository.findByDoneTrue();//itemsToDeleteにdone済みのレコードを格納する
        repository.deleteAll(itemsToDelete);//itemsToDeleteのレコードをすべて削除
    }

}