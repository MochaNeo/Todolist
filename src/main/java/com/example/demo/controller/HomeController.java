package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.TodoItem;
import com.example.demo.form.AddTodoForm;
import com.example.demo.form.SearchForm;
import com.example.demo.form.TodoItemForm;
import com.example.demo.repository.TodoItemRepository;
import com.example.demo.service.TodoItemAddService;
import com.example.demo.service.TodoItemAddValidatorService;
import com.example.demo.service.TodoItemSearchService;
import com.example.demo.service.TodoItemStatusService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

//ViewとServiceの橋渡し的な役割。viewからのリクエストをserviceに送る。
@Controller
@SuppressWarnings("unchecked")
public class HomeController {
	
    @Autowired
    TodoItemRepository repository;
    @PersistenceContext
    EntityManager entityManager;
    //TodoItemService〇〇クラスのインスタンスをDIする
    @Autowired
    private TodoItemStatusService status;
    @Autowired
    private TodoItemAddService add;
    @Autowired
    private TodoItemSearchService search;
    @Autowired
    private TodoItemAddValidatorService validator;
    
    public Map<String, Object> searchConditions = new HashMap<>();
    
    //デフォルトのページ
    @GetMapping("/")
    public String index(@ModelAttribute TodoItemForm todoItemForm, @RequestParam("done") Optional<Boolean> done) {
        todoItemForm.setTodoItems(repository.findByDoneOrderByPriorityDesc(todoItemForm.isDone()));
        return "index";
    }
    
    // todoを検索する
 // todoを検索する
    @PostMapping("/search")
    public String select(@ModelAttribute("searchForm") SearchForm searchForm, Model model) {
        // 検索条件を詰め込むためのMapを作成
        searchConditions.put("title", searchForm.getTitle());
        searchConditions.put("category", searchForm.getCategory());
        searchConditions.put("priority", searchForm.getPriority());
        List<TodoItem> searchResult = search.search(searchConditions);
        // 検索結果をビューに渡す
        model.addAttribute("searchForm", searchForm);
        model.addAttribute("searchResult", searchResult);
        return "index";
    }

    // todoを追加する
    @PostMapping("/new")
    public String newItem(@ModelAttribute("addTodoForm") AddTodoForm addTodoForm, Model model) {
        String ValidationResult = validator.validateAddTodoItem(addTodoForm);
        String result = add.createNewTodoItem(addTodoForm, ValidationResult);
        if (result != null) {
            return "index";
        }
        return "redirect:/";
    }

    
    //アイテムを完了にする
    @PostMapping("/done")
    public String done(@RequestParam("id") long id) {
        status.updateStatus(id, true);
        return "redirect:/?done=false";
    }
    
    //アイテムを未完了にする
    @PostMapping("/restore")
    public String restore(@RequestParam("id") long id) {
        status.updateStatus(id, false);
        return "redirect:/?done=true";
    }
    
    //todoを削除する
    @PostMapping("/delete")
    public String delete(@RequestParam("id") long id) {
    	status.deleteTodo(id);
        return "redirect:/?done=true";
    }
    
    //完了済みのtodoをすべて削除する
    @PostMapping("/allDelete")
    public String allDelete() {
    	status.allDeleteTodo();
    	return "redirect:/?done=true";
    }
}