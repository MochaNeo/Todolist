package com.example.demo.service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TodoItem;
import com.example.demo.repository.TodoRepository;

import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class TodoSearchService {

    @Autowired
    TodoRepository repository;
    @Autowired
    EntityManager entityManager;
    
    
	private String title;
	private int category;
	private int priority;
	private boolean progress;
	

    private static final Map<Integer, String> categoryList = new HashMap<Integer, String>() {{
    	put(1, "work");
    	put(2, "private");
    	put(3, "other");
    }};
    
    private static final Map<Integer, String> priorityList = new HashMap<Integer, String>() {{
    	put(1, "Low");
    	put(2, "Middle");
    	put(3, "High");
    }};
    
    public Map<String, Object> condition = new HashMap<String, Object>() {{
    	put("title", "");
    	put("category", 0);
    	put("priority", 0);
    	put("progress", false);
    }};
    
    public static Map<Integer, String> getCategoryList() {
    	return categoryList;
    }
    
    public static Map<Integer, String> getPriorityList() {
    	return priorityList;
    }
    
    //検索処理の実行
	@SuppressWarnings("unchecked")
	public List<TodoItem> search(Map<String, Object>... org) {
        // 以下のループで、条件が設定されている場合にconditionマップに追加する
        for (int i = 0; i < org.length; i++) {
            for (Map.Entry<String, Object> entry : org[i].entrySet()) {
                // keyを使用して値をconditionに追加
                condition.put(entry.getKey(), entry.getValue());
            }
        }
        setCriteria(condition);
        return repository.search(this);
	}
	
	//conditionの値をsetFieldにすべて代入する
    public void setCriteria(Map<String, Object> condition) {
    	//conditionのkey,valueをsetFieldですべて実行
        for (String fieldName : condition.keySet()) {
            setField(fieldName, condition.get(fieldName));
        }
    }
    
    //リフレクションの処理
    public void setField(String fieldName, Object value) {
    	//クラスフィールドに自動で代入する。tryCatch構文で例外処理を定義する
        try {
            Field field = this.getClass().getDeclaredField(fieldName);//field変数を作成し、同名のconditionKeyを代入する
            field.set(this, value);//fieldと同名のクラスフィールドにconditionのvalueを代入する
        } catch (NoSuchFieldException e) {
        	e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}