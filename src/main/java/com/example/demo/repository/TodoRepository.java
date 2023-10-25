package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.TodoItem;
import com.example.demo.form.TodoSearchCriteria;

import jakarta.persistence.EntityManager;

//データベースへアクセスするためのクラス
//JpaRepositoryクラスを継承してentity名と主キーの型を入れる
public interface TodoRepository extends JpaRepository<TodoItem, Long> {
	public static final EntityManager entityManager = null;
	
	public static final TodoSearchCriteria criteria = new TodoSearchCriteria();
	
	//優先度の降順で完了・未完了のタスクを返す
    public List<TodoItem> findByDoneOrderByPriorityDesc(boolean done);
    
    // JPQLを使用してカスタムのクエリメソッドを定義
    //:#{#criteria.○○}でTodoSearchCriteriaのフィールドにアクセス


    @Query("SELECT b FROM TodoItem b WHERE " +
    "(:#{#criteria.title} IS NULL OR b.title LIKE %:#{#criteria.title}%) " +
    "AND (:#{#criteria.category} = 0 OR b.category = :#{#criteria.category}) " +
    "AND (:#{#criteria.priority} = 0 OR b.priority = :#{#criteria.priority}) " +
    "AND b.done = :#{#criteria.done} " +
    "ORDER BY b.priority DESC")
    
    
    public List<TodoItem> search(@Param("criteria") TodoSearchCriteria criteria);
}

//public static List<TodoItem> search() {
//    StringBuilder queryBuilder = new StringBuilder("SELECT b FROM TodoItem b WHERE 1=1");
//    Map<String, Object> parameters = new HashMap<>();
//
//    if (!criteria.getTitle().equals("")) {
//        queryBuilder.append(" AND b.title LIKE :title");
//    }
//
//    if (criteria.getCategory() != 0) {
//        queryBuilder.append(" AND b.category = :category");
//    }
//
//    if (criteria.getPriority() != 0) {
//        queryBuilder.append(" AND b.priority = :priority");
//    }
//
//    queryBuilder.append(" AND b.done = :done");
//
//    queryBuilder.append(" ORDER BY b.priority DESC");
//
//    TypedQuery<TodoItem> query = entityManager.createQuery(queryBuilder.toString(), TodoItem.class);
//
//    for (Map.Entry<String, Object> entry : parameters.entrySet()) {
//        query.setParameter(entry.getKey(), entry.getValue());
//    }
//
//    return query.getResultList();
//	}
