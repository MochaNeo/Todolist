package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.TodoItem;


//データベースへアクセスするためのクラス
//JpaRepositoryクラスを継承してentity名と主キーの型を入れる
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
    public List<TodoItem> findByDoneOrderByPriorityDesc(boolean done);
}