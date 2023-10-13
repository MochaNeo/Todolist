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
import com.example.demo.service.TodoItemService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

//ViewとServiceの橋渡し的な役割。viewからのリクエストをserviceに送る。
@Controller
public class HomeController {

    @Autowired
    private TodoItemRepository repository;
    @Autowired
    TodoItemService todoItemService;
    @PersistenceContext
    EntityManager entityManager;
    
    //デフォルトのページ
    @GetMapping("/")
    public String index(@ModelAttribute TodoItemForm todoItemForm, @RequestParam("done") Optional<Boolean> done) {
        todoItemForm.setTodoItems(this.repository.findByDoneOrderByPriorityDesc(todoItemForm.isDone()));
        return "index";
    }
    
    //完了したアイテムを表示する
    @PostMapping(value = "/done")
    public String done(@RequestParam("id") long id) {
        Optional<TodoItem> itemOptional = this.repository.findById(id);
        if (itemOptional.isPresent()) {
            TodoItem item = itemOptional.get();
            item.setDone(true);
            this.repository.save(item);
        }
        return "redirect:/?done=false";
    }
    
    //未完了のアイテムを表示する
    @PostMapping(value = "/restore")
    public String restore(@RequestParam("id") long id) {
        Optional<TodoItem> itemOptional = this.repository.findById(id);
        if (itemOptional.isPresent()) {
            TodoItem item = itemOptional.get();
            item.setDone(false);
            this.repository.save(item);
        }
        return "redirect:/?done=true";
    }
    
    //todoを追加する
    @PostMapping(value = "/new")
    public String newItem(@ModelAttribute TodoItemForm todoItemForm, TodoItem item) {
    	String result = todoItemService.createNewTodoItem(item, todoItemForm.isDone());
    	
    	if (result != null) {
    		todoItemForm.setErrorMessage(result);
    		todoItemForm.setTodoItems(todoItemService.getTodoItems(todoItemForm.isDone()));
    		return "index";
    	}
    	return "redirect:/";
    }
    
    //todoを削除する
    @PostMapping(value = "/delete")
    @Transactional
    public String delete(@RequestParam("id") long id) {
        Optional<TodoItem> itemOptional = this.repository.findById(id);
        if (itemOptional.isPresent()) {
            TodoItem item = itemOptional.get();
            this.repository.delete(item);         }
        return "redirect:/";
    }
    
  //検索結果の受け取り処理
    @PostMapping(value = "/search")
    public String select(@ModelAttribute("formModel") TodoItemForm todoItemForm, Model model) {
        List<TodoItem> searchResult = todoItemService.search(todoItemForm.getTitle(), todoItemForm.getCategory(),
        												todoItemForm.getPriority(),todoItemForm.isDone());
        todoItemForm.setTodoItems(searchResult); // 検索結果をセット
        model.addAttribute("todoItemForm", todoItemForm);
        return "index";
    }
}