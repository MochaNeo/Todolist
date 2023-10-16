package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

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
    
    //todoを検索
    @SuppressWarnings("unchecked")
    public List<TodoItem> search(String title, int category, int priority, boolean done) {
        List<TodoItem> result = new ArrayList<TodoItem>();
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT b FROM TodoItem b WHERE ");
        boolean titleFlg = false;
        boolean categoryFlg = false;
        boolean priorityFlg = false;
        boolean doneFlg = false;
        boolean andFlg = false;
        
        if (!"".equals(title)) {
            sql.append("b.title LIKE :title");
            titleFlg = true;
            andFlg = true;
        }
        if (0 != category) {
            if (andFlg) sql.append(" AND ");
            sql.append("b.category LIKE :category");
            categoryFlg = true;
            andFlg = true;
        }
        if (0 != priority) {
            if (andFlg) sql.append(" AND ");
            sql.append("b.priority = :priority");
            priorityFlg = true;
            andFlg = true;
        }
        if (done) {
            if (andFlg) sql.append(" AND ");
            sql.append("b.done = :done");
            doneFlg = true;
        }
        if (!"".equals(title) || category != 0 || priority != 0 || done) {
            Query query = entityManager.createQuery(sql.toString());
            if (titleFlg) query.setParameter("title", "%" + title + "%");
            if (categoryFlg) query.setParameter("category", category);
            if (priorityFlg) query.setParameter("priority", priority);
            if (doneFlg) query.setParameter("done", done);
            result = query.getResultList();
            
        } else {
            result = repository.findAll();
        }
        return result;
    }
}