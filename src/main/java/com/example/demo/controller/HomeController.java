package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.TodoItem;
import com.example.demo.form.TodoItemForm;
import com.example.demo.repository.TodoRepository;
import com.example.demo.service.TodoAddService;
import com.example.demo.service.TodoAddValidatorService;
import com.example.demo.service.TodoSearchService;
import com.example.demo.service.TodoStatusService;

@Controller
@SuppressWarnings("unchecked")
public class HomeController {
//他クラスのDI
    @Autowired
    TodoRepository repository;
    
    @Autowired
    private TodoStatusService status;
    
    @Autowired
    private TodoAddService add;
    
    @Autowired
    private TodoSearchService search;
    
    @Autowired
    private TodoAddValidatorService validator;
    
    //検索時の条件保存用MAP
    public Map<String, Object> searchConditions = new HashMap<>();
    
    
    
    //デフォルトのページ
    @GetMapping("/hello")
    public String hello(@ModelAttribute TodoItemForm todoItemForm) {
        todoItemForm.setTodoItems(repository.findByDoneOrderByPriorityDesc(todoItemForm.isDone()));
        return "hello";
    }
    
    
    
    //todoを検索する
    @PostMapping("/searchTodo")
    public String searchTodo(@ModelAttribute TodoItemForm todoItemForm) {
    		searchConditions.put("title", todoItemForm.getSearchTitle());
    		searchConditions.put("category", todoItemForm.getSearchCategory());
    		searchConditions.put("priority", todoItemForm.getSearchPriority());
    	List<TodoItem> searchResult = search.search(searchConditions);
    	todoItemForm.setTodoItems(searchResult);
        return "hello";
    }
    
    
    
    // todoを追加する
    @PostMapping("/new")
    public String newItem(@ModelAttribute TodoItemForm todoItemForm, TodoItem item) {
        String ValidationResult = validator.validateAddTodoItem(item);
        String result = add.createNewTodoItem(todoItemForm, item, ValidationResult);
        if (result != null) {
        	return "hello";
        }
        return "redirect:/hello";
    }
    
    
    
    //アイテムを完了にする
    @PostMapping("/done")
    public String done(@RequestParam("id") long id) {
        status.updateStatus(id, true);
        return "redirect:/hello?done=false";
    }
    
    
    
    //アイテムを未完了にする
    @PostMapping("/restore")
    public String restore(@RequestParam("id") long id) {
        status.updateStatus(id, false);
        return "redirect:/hello?done=true";
    }
    
    
    
    //todoを削除する
    @PostMapping("/delete")
    public String delete(@RequestParam("id") long id) {
    	status.deleteTodo(id);
        return "redirect:/hello?done=true";
    }
    
    
    
    //完了済みのtodoをすべて削除する
    @PostMapping("/allDelete")
    public String allDelete() {
    	status.allDeleteTodo();
    	return "redirect:/hello?done=true";
    }
}
