package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.TodoItem;
import com.example.demo.service.TodoSearchService;

import jakarta.persistence.EntityManager;

//データベースへアクセスするのクラス
//JpaRepositoryクラスを継承してentity名と主キーの型を入れる
public interface TodoRepository extends JpaRepository<TodoItem, Long> {
	public static final EntityManager entityManager = null;
	
	public static final TodoSearchService service = new TodoSearchService();
	
	//優先度の降順で完了・未完了のタスクを返す
    public List<TodoItem> findByProgressOrderByPriorityDesc(boolean done);
    
    
    // JPQLを使用してカスタムのクエリメソッドを定義
    //WHERE 0=0は真となり全件取得する

    @Query("SELECT b FROM TodoItem b WHERE " +
    "b.title LIKE %:#{#service.title}% " +
    "AND (:#{#service.category} = 0 OR b.category = :#{#service.category}) " +
    "AND (:#{#service.priority} = 0 OR b.priority = :#{#service.priority}) " +
    "AND b.progress = :#{#service.progress} " +
    "ORDER BY b.priority DESC")
    public List<TodoItem> search(@Param("service") TodoSearchService service);
}
