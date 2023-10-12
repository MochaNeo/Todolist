package com.example.demo.dao;

import java.io.Serializable;
import java.util.List;

import com.example.demo.entity.TodoItem;

//Data Access Objectの略称。データアクセスロジックのみの役割を持つ
public interface TodoItemDao extends Serializable {
	public List<TodoItem> search(String title, String category, String priority, boolean done);
}