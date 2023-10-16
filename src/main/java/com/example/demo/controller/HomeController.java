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
import com.example.demo.service.TodoItemServiceDoneStatus;
import com.example.demo.service.TodoItemServiceSearch;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

//ViewとServiceの橋渡し的な役割。viewからのリクエストをserviceに送る。
@Controller
public class HomeController {

    @Autowired
    TodoItemRepository repository;
    @PersistenceContext
    EntityManager entityManager;
    
    //デフォルトのページ
    @GetMapping("/")
    public String index(@ModelAttribute TodoItemForm todoItemForm, @RequestParam("done") Optional<Boolean> done) {
        todoItemForm.setTodoItems(this.repository.findByDoneOrderByPriorityDesc(todoItemForm.isDone()));
        return "index";
    }
    
    
    //完了したアイテムを表示する
    @PostMapping(value = "/updateDoneStatus")
    public String updateDoneStatus(@RequestParam("id") long id, @RequestParam("done") boolean done) {
        TodoItemServiceDoneStatus todoItemServiceDoneStatus = new TodoItemServiceDoneStatus();
		todoItemServiceDoneStatus.updateDoneStatus(id, done);
        return "redirect:/?done=" + done;
    }
    
    
    //todoを追加する
    @Autowired
    private TodoItemServiceAdd todoItemServiceAdd;// TodoItemServiceAddクラスのインスタンスをDIする
    @PostMapping(value = "/new")
    public String newItem(@ModelAttribute TodoItemForm todoItemForm, TodoItem item) {
        String result = todoItemServiceAdd.createNewTodoItem(item, todoItemForm.isDone());
        if (result != null) {
            todoItemForm.setErrorMessage(result);
            todoItemForm.setTodoItems(todoItemServiceAdd.getTodoItems(todoItemForm.isDone()));
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
    
    
    //todoを検索する
    @Autowired
    private TodoItemServiceSearch todoItemServiceSearch; // TodoItemServiceSearchクラスのインスタンスをDIする
    @PostMapping(value = "/search")
    public String select(@ModelAttribute("formModel") TodoItemForm todoItemForm, Model model) {
        List<TodoItem> searchResult = todoItemServiceSearch.search(todoItemForm.getTitle(),
            todoItemForm.getCategory(),
            todoItemForm.getPriority(),
            todoItemForm.isDone());
        todoItemForm.setTodoItems(searchResult); // 検索結果をセット
        model.addAttribute("todoItemForm", todoItemForm);
        return "index";
    }

}