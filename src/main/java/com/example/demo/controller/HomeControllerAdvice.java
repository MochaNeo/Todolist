package com.example.demo.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.service.TodoItemSearchService;

//Controller共通の処理をまとめる
@ControllerAdvice
public class HomeControllerAdvice {

    @ModelAttribute("categoryMap")
    public Map<Integer, String> getCategory() {
        return TodoItemSearchService.getCategory();
    }

    @ModelAttribute("priorityMap")
    public Map<Integer, String> getPriority() {
        return TodoItemSearchService.getPriority();
    }
}