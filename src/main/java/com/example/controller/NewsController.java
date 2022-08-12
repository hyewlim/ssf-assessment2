package com.example.controller;

import com.example.model.Article;
import com.example.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService service;

    @GetMapping ("/")
    public String index(Model model) {
        Article article = new Article();
        List<Article> articles = service.getArticles();
        model.addAttribute("article", articles);
        return "index";
    }

    @PostMapping ("/articles")
    public String saveArticles(Model model) {
        Article article = new Article();
        List<Article> articles = service.getArticles();
        service.saveArticles(articles);

        return "index";
    }
}
