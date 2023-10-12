package com.example.demo.controller;

import java.sql.Date;
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
    public String index(@ModelAttribute TodoItemForm todoItemForm, @RequestParam("isDone") Optional<Boolean> isDone) {
        todoItemForm.setDone(isDone.isPresent() ? isDone.get() : false);
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
        return "redirect:/?isDone=false";
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
        return "redirect:/?isDone=true";
    }
    
    
    //todoを追加する
    @PostMapping(value = "/new")
    @Transactional
    public String newItem(@ModelAttribute TodoItemForm todoItemForm, TodoItem item) {
        String category = item.getCategory();
        String title = item.getTitle();
        String description = item.getDescription();
        String priority = item.getPriority();
        Date dueDate = item.getDueDate();
        Date yesterday = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        
		if (title == null || title.trim().isEmpty()) {
            // エラーメッセージを設定して、再度表示
            todoItemForm.setErrorMessage("タイトルを入力してください。");
            todoItemForm.setTodoItems(this.repository.findByDoneOrderByPriorityDesc(todoItemForm.isDone())); // タスクリストを再取得
            return "index";
        } else if (title.trim().toLowerCase().contains("宿題")) {
            // NGワードが含まれている場合、エラーメッセージを設定して、再度表示
            todoItemForm.setErrorMessage("NGワード「宿題」は設定できません。");
            todoItemForm.setTodoItems(this.repository.findByDoneOrderByPriorityDesc(todoItemForm.isDone())); // タスクリストを再取得
            return "index";
        } else if (dueDate.before(yesterday)) {
                // 期日が昨日以前の場合、エラーメッセージを設定して、再度表示
            todoItemForm.setErrorMessage("期日が昨日以前の日付です。有効な日付を設定してください。");
            todoItemForm.setTodoItems(this.repository.findByDoneOrderByPriorityDesc(todoItemForm.isDone())); // タスクリストを再取得
            return "index";
        } else {
        item.setCategory(category);
        item.setDescription(description);
        item.setDueDate(dueDate);
        item.setPriority(priority);
        item.setDone(false);
        this.repository.save(item);
        
        return "redirect:/";
        }
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
        List<TodoItem> result = todoItemService.search(todoItemForm.getTitle(), 
        												todoItemForm.getCategory(),
        												todoItemForm.getPriority(),
        												todoItemForm.isDone());
        todoItemForm.setTodoItems(result); // 検索結果をセット
        model.addAttribute("todoItemForm", todoItemForm);
        return "index";
    }


}