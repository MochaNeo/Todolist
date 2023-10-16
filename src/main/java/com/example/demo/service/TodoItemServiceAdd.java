package com.example.demo.service;

import java.util.Date;
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
	
	//todoを追加
    @Transactional
    public String createNewTodoItem(TodoItem item, boolean isDone) {
    	String title = item.getTitle();
    	Date dueDate = item.getDueDate();
    	Date yesterday = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
    	
    		if (title == "") {
    			return "タイトルを入力してください。";
    		} else if (title.trim().toLowerCase().contains("")) {
    		    return "タイトルに空白が含まれています";
    		} else if (title.trim().toLowerCase().contains("宿題")) {
    			return "NGワード「宿題」は設定できません。";
    		} else if (dueDate.before(yesterday)) {
    			return "期日が昨日以前の日付です。有効な日付を設定してください。";
    		}
    	item.setDone(false);
    	repository.save(item);
    	return null; // エラーメッセージがない場合は null を返す
        }
    
    //getTodoItemsでisDoneの内容を降順で表示する
    public List<TodoItem> getTodoItems(boolean isDone) {
    	return repository.findByDoneOrderByPriorityDesc(isDone);
    }
}