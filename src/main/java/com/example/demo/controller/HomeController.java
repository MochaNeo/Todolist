package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.TodoItem;
import com.example.demo.form.TodoItemForm;
import com.example.demo.repository.TodoRepository;
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
    private TodoSearchService search;

    //デフォルトのページ
    //modelattributeはtodoitemformにform(postされた値があれば)を代入する。getアクセスの場合にはnewされ、todoitemformインスタンスが作成される)
    @GetMapping("/")
    public ModelAndView hello(@ModelAttribute TodoItemForm todoItemForm, ModelAndView mav) {
        mav.setViewName("hello");
        List<TodoItem> todo = repository.findByProgressOrderByPriorityDesc(false);
        List<TodoItem> done = repository.findByProgressOrderByPriorityDesc(true);
        mav.addObject("todo", todo);
        mav.addObject("done", done);
        return mav;
    }



    //todoを検索する
    @PostMapping("/searchTodo")
    public ModelAndView searchTodo(@ModelAttribute TodoItemForm searchForm, ModelAndView mav) {
        //検索用のmapを作成する
        Map<String, Object> searchConditions = new HashMap<>();
            //searchConditionsにformでポストされたtitle, category, priorityの値を入れる。
            searchConditions.put("title", searchForm.getSearchTitle());
            searchConditions.put("category", searchForm.getSearchCategory());
            searchConditions.put("priority", searchForm.getSearchPriority());
        //TodoSearchServiceで検索を行う。searchConditionsを渡し、searchResultリストに代入する
    	List<TodoItem> searchResult = search.search(searchConditions);
        //検索結果をsetTodoItemsに代入する。searchFlagをtrueにする。（setTodoItemsの値はテーブルに表示される(hello.html)）
    	searchForm.setTodoItems(searchResult);
        boolean searchFlag = true;
        mav.addObject("searchFlag", searchFlag);
        mav.setViewName("hello");
        return mav;
    }



    // todoを追加する
    @PostMapping("/new")
    public String newItem(@ModelAttribute @Validated TodoItemForm addForm, BindingResult result, Model model, TodoItem item) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            model.addAttribute("errorMessage", "入力エラーがあります。");
            return "/hello";
        }
        item.setProgress(false);
        repository.save(item);
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
