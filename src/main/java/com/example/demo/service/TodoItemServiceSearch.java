package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    
    
    //postの入力値の有無
    @SuppressWarnings("unchecked")
    public List<TodoItem> search(String title, int category, int priority, boolean done) {
        List<TodoItem> result = new ArrayList<TodoItem>();

        StringBuilder sql = buildQuery(title, category, priority, done);
        
        if (!"".equals(title) || category != 0 || priority != 0) {
            Query query = entityManager.createQuery(sql.toString());
            setQueryParameters(query, title, category, priority, done);
            result = query.getResultList();
        } else {
            result = repository.findAll(Sort.by(Sort.Direction.DESC, "priority"));
        }
        return result;
    }
    
    
    //クエリの組み立て
    private StringBuilder buildQuery(String title, int category, int priority, boolean done) {
        StringBuilder sql = new StringBuilder("SELECT b FROM TodoItem b WHERE ");
        boolean andFlg = false;

        if (!"".equals(title)) {
            sql.append("b.title LIKE :title");
            andFlg = true;
        }
        if (0 != category) {
            if (andFlg) sql.append(" AND ");
            sql.append("b.category LIKE :category");
            andFlg = true;
        }
        if (0 != priority) {
            if (andFlg) sql.append(" AND ");
            sql.append("b.priority = :priority");
            andFlg = true;
        }
        if (done) {
            if (andFlg) sql.append(" AND ");
            sql.append("b.done = :done");
        }
        return sql;
    }
    private void setQueryParameters(Query query, String title, int category, int priority, boolean done) {
        if (!"".equals(title)) query.setParameter("title", "%" + title + "%");
        if (0 != category) query.setParameter("category", category);
        if (0 != priority) query.setParameter("priority", priority);
        if (done) query.setParameter("done", done);
    }
}
