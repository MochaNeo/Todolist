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
    
    
    //categoryの項目の管理
    public static Map<Integer, String> getCategory() {
        Map<Integer, String> category = new HashMap<Integer, String>();
        category.put(1, "work");
        category.put(2, "private");
        category.put(3, "other");
        return category;
    }
    //priorityの項目の管理
    public static Map<Integer, String> getPriority() {
        Map<Integer, String> priority = new HashMap<Integer, String>();
        priority.put(1, "Low");
        priority.put(2, "Middle");
        priority.put(3, "High");
        return priority;
    }
    
    
    //postの入力値の有無
    public List<TodoItem> search(String title, int category, int priority) {
        // TodoItemRepositoryのsearchItemsメソッドを呼び出し
        return repository.searchItems(title, category, priority);
    }

}