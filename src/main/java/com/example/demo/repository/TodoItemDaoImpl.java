package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.TodoItemDao;
import com.example.demo.entity.TodoItem;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class TodoItemDaoImpl implements TodoItemDao {
	
	//entityを使用するために必要な機能を提供する
	@Autowired
	EntityManager entityManager;
	
	public TodoItemDaoImpl() {
		super();
	}
	
	public TodoItemDaoImpl(EntityManager manager) {
		this();
		entityManager = manager;
	}
	
	//Daoクラスで用意したsearchメソッドをオーバーライドする
	@SuppressWarnings("unchecked")
	@Override
	public List<TodoItem> search(String title, String category, String priority) {
		//stringBuilderでsql文を連結する
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT b FROM TodoItem b WHERE ");
		
		boolean titleFlg = false;
		boolean categoryFlg = false;
		boolean priorityFlg = false;
		boolean andFlg = false;
		
		
		if(!"".equals(title)) {
			sql.append("b.title LIKE :title");
			titleFlg = true;
			andFlg   = true;
		}
		
		if(!"".equals(category)) {
            if (andFlg) sql.append(" AND ");
            sql.append("b.category LIKE :category");
            categoryFlg = true;
            andFlg    = true;
        }
		
		if(!"".equals(priority)) {
            if (andFlg) sql.append(" AND ");
            sql.append("b.priority LIKE :priority");
            priorityFlg = true;
            andFlg   = true;
        }

		Query query = entityManager.createQuery(sql.toString());
		
		if (titleFlg) query.setParameter("title", "%" + title + "%");
        if (categoryFlg) query.setParameter("category", "%" + category + "%");
        if (priorityFlg) query.setParameter("priority", "%" + priority + "%");
        return query.getResultList();
	}
}