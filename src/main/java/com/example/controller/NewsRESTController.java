package com.example.controller;

import com.example.model.Article;
import com.example.service.NewsService;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/news")
public class NewsRESTController {

    private static final Logger logger = LoggerFactory.getLogger(NewsRESTController.class);

    @Autowired
    private NewsService service;

    @GetMapping ("/{id}")
    public ResponseEntity<?> getArticle(@PathVariable String id) {

        Optional<Article> article = service.retrieveArticle(id);

        if (article.isEmpty()) {
            JsonObject err = Json.createObjectBuilder()
                    .add("error", "Cannot find news article %s".formatted(id)).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err.toString());
        }

        Article article1 = article.get();
        return ResponseEntity.ok(article1);
    }

    @GetMapping
    public ResponseEntity<?> getAllArticles() {

        List<Article> output = service.getArticles();

        return ResponseEntity.ok(output);

    }



}
