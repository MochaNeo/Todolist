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
    
    private static Map<String, Object> condition = new HashMap<String, Object>() {{
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
    
    
    //postの入力値の有無
    @SuppressWarnings("unchecked")
	public List<TodoItem> search(Map<String, Object> ...org) {
        // 以下のループで、条件が設定されている場合にconditionマップに追加する
        for (int i = 0; i < org.length; i++) {
        	String key = org[i].keySet().iterator().next();
        	// keyを使用して値をconditionに追加
        	condition.put(key, org[i].get(key));
        }
        // TodoItemRepositoryのsearchItemsメソッドを呼び出し、conditionの値を渡す
        return repository.searchItems((String) condition.get("title"),
        								(int) condition.get("category"),
        								(int) condition.get("priority"));
    }
}