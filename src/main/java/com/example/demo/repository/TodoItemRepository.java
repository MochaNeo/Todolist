package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.TodoItem;


//データベースへアクセスするためのクラス
//JpaRepositoryクラスを継承してentity名と主キーの型を入れる
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
	
	//優先度の降順で完了・未完了のタスクを返す
    public List<TodoItem> findByDoneOrderByPriorityDesc(boolean done);
    
    
    // JPQLを使用してカスタムのクエリメソッドを定義
    @Query("SELECT b FROM TodoItem b WHERE " +
           "(:title IS NULL OR b.title LIKE %:title%) " +
           "AND (:category = 0 OR b.category = :category) " +
           "AND (:priority = 0 OR b.priority = :priority) " +
           "AND b.done = false " +
           "ORDER BY b.priority DESC")
    public List<TodoItem> searchItems(
        @Param("title") String title,
        @Param("category") int category,
        @Param("priority") int priority
    );
    
    
    //doneがtrueのレコードをすべて取得
	public List<TodoItem> findByDoneTrue();
}