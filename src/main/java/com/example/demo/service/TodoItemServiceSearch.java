package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TodoItem;
import com.example.demo.repository.TodoItemRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class TodoItemServiceSearch {

    @Autowired
    TodoItemRepository repository;
    @Autowired
    EntityManager entityManager;
    
    
    //categoryの項目の管理
    public Map<Integer, String> getCategory() {
        Map<Integer, String> category = new HashMap<Integer, String>();
        category.put(1, "work");
        category.put(2, "private");
        category.put(3, "other");
        return category;
    }
    
    //priorityの項目の管理
    public Map<Integer, String> getPriority() {
        Map<Integer, String> priority = new HashMap<Integer, String>();
        priority.put(1, "Low");
        priority.put(2, "Middle");
        priority.put(3, "High");
        return priority;
    }

    
    //postの入力値の有無
    @SuppressWarnings("unchecked")
    public List<TodoItem> search(String title, int category, int priority, boolean done) {
        List<TodoItem> result = new ArrayList<TodoItem>();

        StringBuilder sql = TodoItemRepository.buildQuery(title, category, priority, done);
        
        if (!"".equals(title) || category != 0 || priority != 0) {
            Query query = entityManager.createQuery(sql.toString());
            TodoItemRepository.setQueryParameters(query, title, category, priority, done);
            result = query.getResultList();
        } else {
            result = repository.TodoItemSort();
        }
        return result;
    }
}
