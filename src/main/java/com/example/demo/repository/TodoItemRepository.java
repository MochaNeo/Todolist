package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.TodoItem;

import jakarta.persistence.Query;


//データベースへアクセスするためのクラス
//JpaRepositoryクラスを継承してentity名と主キーの型を入れる
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
    public List<TodoItem> findByDoneOrderByPriorityDesc(boolean done);
    
    
    //todoitemをpriorityの降順で取得する
    public default List<TodoItem> TodoItemSort() {
    	return this.findAll(Sort.by(Sort.Direction.DESC, "priority"));
    }
    
    
    //クエリ作成
    public static StringBuilder buildQuery(String title, int category, int priority, boolean done) {
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
    public static void setQueryParameters(Query query, String title, int category, int priority, boolean done) {
        if (!"".equals(title)) query.setParameter("title", "%" + title + "%");
        if (0 != category) query.setParameter("category", category);
        if (0 != priority) query.setParameter("priority", priority);
        if (done) query.setParameter("done", done);
    }
}