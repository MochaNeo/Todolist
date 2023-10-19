package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.TodoItem;
import com.example.demo.form.TodoItemForm;
import com.example.demo.repository.TodoItemRepository;
import com.example.demo.service.TodoItemServiceAdd;
import com.example.demo.service.TodoItemServiceAddValidator;
import com.example.demo.service.TodoItemServiceSearch;
import com.example.demo.service.TodoItemServiceStatus;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

//ViewとServiceの橋渡し的な役割。viewからのリクエストをserviceに送る。
@Controller
public class HomeController {
	
    @Autowired
    TodoItemRepository repository;
    @PersistenceContext
    EntityManager entityManager;
    //TodoItemService〇〇クラスのインスタンスをDIする
    @Autowired
    private TodoItemServiceStatus todoItemServiceStatus;
    @Autowired
    private TodoItemServiceAdd todoItemServiceAdd;
    @Autowired
    private TodoItemServiceSearch todoItemServiceSearch;
    @Autowired
    private TodoItemServiceAddValidator validator;
    
    
    //デフォルトのページ
    @GetMapping("/")
    public String index(@ModelAttribute TodoItemForm todoItemForm, @RequestParam("done") Optional<Boolean> done, Model model) {
        todoItemForm.setTodoItems(repository.findByDoneOrderByPriorityDesc(todoItemForm.isDone()));
        model.addAttribute("todoItemForm", todoItemForm);
        return "index";
    }
    
    // todoを検索する
    @PostMapping("/search")
    public String select(@ModelAttribute("formModel") TodoItemForm todoItemForm, Model model) {
        List<TodoItem> searchResult = todoItemServiceSearch.search(todoItemForm.getTitle(),todoItemForm.getCategory(),todoItemForm.getPriority());
        todoItemForm.setTodoItems(searchResult); // 検索結果をセット
        model.addAttribute("todoItemForm", todoItemForm);
        return "index";
    }
    
    //アイテムを完了にする
    @PostMapping("/done")
    public String done(@RequestParam("id") long id) {
        todoItemServiceStatus.updateStatus(id, true);
        return "redirect:/?done=false";
    }
    
    //アイテムを未完了にする
    @PostMapping("/restore")
    public String restore(@RequestParam("id") long id) {
        todoItemServiceStatus.updateStatus(id, false);
        return "redirect:/?done=true";
    }
    
    // todoを追加する
    @PostMapping("/new")
    public String newItem(@ModelAttribute TodoItemForm todoItemForm, TodoItem item) {
    	String ValidationResult = validator.validateAddTodoItem(item);
        String result = todoItemServiceAdd.createNewTodoItem(todoItemForm, item, ValidationResult);
        if (result != null) {
        	return "index";
        }	return "redirect:/";
    }
    
    //todoを削除する
    @PostMapping("/delete")
    public String delete(@RequestParam("id") long id) {
    	todoItemServiceStatus.deleteTodo(id);
        return "redirect:/?done=true";
    }
}