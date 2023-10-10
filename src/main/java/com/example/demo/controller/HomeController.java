package com.example.demo.controller;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.TodoItem;
import com.example.demo.form.TodoItemForm;
import com.example.demo.repository.TodoItemRepository;

import jakarta.transaction.Transactional;

//フロントエンドとバックエンドの入出力の管理を行うControllerクラス
@Controller
public class HomeController {

    @Autowired
    TodoItemRepository repository;
    
    
    //デフォルトのページ
    @GetMapping("/")
    //ModelAttributeでTodoitemformの3つのフィールドをtodoItemFormに渡す。todoItemFormが変数名
    //クライアントが入力したisDoneを受け取り、boolean型のisDoneに格納する。Optionalとは値が存在しない可能性があることを示す
    public String index(@ModelAttribute TodoItemForm todoItemForm, @RequestParam("isDone") Optional<Boolean> isDone) {
        todoItemForm.setDone(isDone.isPresent() ? isDone.get() : false);
        //isDoneがある時はtrueない時はfalseを返す。新規にsetするときとかはfalseになる
        todoItemForm.setTodoItems(this.repository.findByDoneOrderByPriorityDesc(todoItemForm.isDone()));
        //TodoItemFormクラスのsetDoneを呼び出し上記の結果をtodoItemFormインスタンスのisDoneフィールドに設定している。
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
    
    
    //todoを表示する
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
        Integer priority = item.getPriority();
        Date dueDate = item.getDueDate();


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
        } else {
        // ここで item を保存する処理
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
            this.repository.delete(item); // タスクを削除
        }
        return "redirect:/?isDone=false"; // リダイレクト
    }
}