package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.TodoItem;

//データベースへアクセスするためのクラス
//ほぼテンプレみたいな感じだと思う。JpaRepositoryっていうクラスを継承してentity名と主キーの型を入れる
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
	//下記はtodoテーブルから持ってきた完了・未完了のtodoを昇順で表示するために記載している。
	//TodoItem(Entity)リストからdoneがtrueまたはfalseのtaskを呼び出すことができる。
    public List<TodoItem> findByDoneOrderByPriorityDesc(boolean done);
}