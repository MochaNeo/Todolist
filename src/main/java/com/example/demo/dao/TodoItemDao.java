package com.example.demo.dao;

import java.io.Serializable;
import java.util.List;

import com.example.demo.entity.TodoItem;

//タイトル、カテゴリー、優先度で検索するためのクラス
public interface TodoItemDao extends Serializable {
	public List<TodoItem> search(String title, String category, String priority);
}
