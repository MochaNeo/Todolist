package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TodoItem;
import com.example.demo.repository.TodoItemRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Service
public class TodoItemService {
	
    @Autowired
    TodoItemRepository repository;
    @Autowired
    EntityManager entityManager;
    
    //todoを追加
    @Transactional
    public String createNewTodoItem(TodoItem item, boolean isDone) {
    	String title = item.getTitle();
    	Date dueDate = item.getDueDate();
    	Date yesterday = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
    	
    		if (title == "") {
    			return "タイトルを入力してください。";
    		} else if (title.trim().toLowerCase().contains("")) {
    			return "タイトルに空白が含まれています";
    		} else if (title.trim().toLowerCase().contains("宿題")) {
    			return "NGワード「宿題」は設定できません。";
    		} else if (dueDate.before(yesterday)) {
    			return "期日が昨日以前の日付です。有効な日付を設定してください。";
    		}
    	item.setDone(false);
    	repository.save(item);
    	return null; // エラーメッセージがない場合は null を返す
        }
    
    //todoを検索
    @SuppressWarnings("unchecked")
    public List<TodoItem> search(String title, int category, int priority, boolean done) {
        List<TodoItem> result = new ArrayList<TodoItem>();
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT b FROM TodoItem b WHERE ");
        boolean titleFlg = false;
        boolean categoryFlg = false;
        boolean priorityFlg = false;
        boolean doneFlg = false;
        boolean andFlg = false;
        
        if (!"".equals(title)) {
            sql.append("b.title LIKE :title");
            titleFlg = true;
            andFlg = true;
        }
        if (0 != category) {
            if (andFlg) sql.append(" AND ");
            sql.append("b.category LIKE :category");
            categoryFlg = true;
            andFlg = true;
        }
        if (0 != priority) {
            if (andFlg) sql.append(" AND ");
            sql.append("b.priority = :priority");
            priorityFlg = true;
            andFlg = true;
        }
        if (done) {
            if (andFlg) sql.append(" AND ");
            sql.append("b.done = :done");
            doneFlg = true;
        }
        if (!"".equals(title) || category != 0 || priority != 0 || done) {
            Query query = entityManager.createQuery(sql.toString());
            if (titleFlg) query.setParameter("title", "%" + title + "%");
            if (categoryFlg) query.setParameter("category", category);
            if (priorityFlg) query.setParameter("priority", priority);
            if (doneFlg) query.setParameter("done", done);
            result = query.getResultList();
            
        } else {
            result = repository.findAll();
        }
        return result;
    }

    //getTodoItemsでisDoneの内容を降順で表示する
    public List<TodoItem> getTodoItems(boolean isDone) {
    	return repository.findByDoneOrderByPriorityDesc(isDone);
    }

    //完了したアイテム、未完了のアイテムを表示
    @Transactional
    public void updateDoneStatus(long id, boolean done) {
    	java.util.Optional<TodoItem> itemOptional = repository.findById(id);
    		if (itemOptional.isPresent()) {
    			TodoItem item = itemOptional.get();
    			item.setDone(done);
    			repository.save(item);
            }
    	}
}