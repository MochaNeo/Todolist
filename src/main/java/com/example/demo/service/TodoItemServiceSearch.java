package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TodoItem;
import com.example.demo.repository.TodoItemRepository;

import jakarta.persistence.EntityManager;


@Service
public class TodoItemServiceSearch {

    @Autowired
    TodoItemRepository repository;
    @Autowired
    EntityManager entityManager;
    
    private static final Map<Integer, String> category = new HashMap<Integer, String>() {{
    	put(1, "work");
    	put(2, "private");
    	put(3, "other");
    }};
    
    private static final Map<Integer, String> priority = new HashMap<Integer, String>() {{
    	put(1, "Low");
    	put(2, "Middle");
    	put(3, "High");
    }};
    
    private static final Map<String, Object> condition = new HashMap<String, Object>() {{
    	put("title", "");
    	put("category", 0);
    	put("priority", 0);
    }};
    
    public static Map<Integer, String> getCategory() {
    	return category;
    }
    
    public static Map<Integer, String> getPriority() {
    	return priority;
    }
    
    public static Map<String, Object> getCondition(){
    	return condition;
    }
    
    //postの入力値の有無
	public List<TodoItem> search(String title, int category, int priority) {
    	condition.put("title", title);
    	condition.put("category", category);
    	condition.put("priority", priority);
    	
    	
    	//TodoItemRepositoryのsearchItemsメソッドを呼び出し
    	//condition配列の各要素を取り出しsearchItemsに代入
    	
        return repository.searchItems((String)condition.get("title"), (int)condition.get("category"), (int)condition.get("priority"));
    }
}