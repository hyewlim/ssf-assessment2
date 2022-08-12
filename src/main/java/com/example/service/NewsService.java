package com.example.service;

import com.example.model.Article;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    private static final String URL = "https://min-api.cryptocompare.com/data/v2/news/";

    private static final String HASH_KEY = "Article";

    @Autowired
    @Qualifier("redislab")
    private RedisTemplate template;

    @Value("${CRYPTO_API_KEY}")
    private String apiKey;

    private Boolean hasKey;

    @PostConstruct
    private void init() {
        hasKey = null != apiKey;
        logger.info(">>> API KEY set: " + hasKey);
    }

    public List<Article> getArticles() {

        String payload;

        try {

            String queryUrl = UriComponentsBuilder.fromUriString(URL)
                    .queryParam("lang", "EN")
                    .queryParam("api_key", apiKey)
                    .toUriString();

            logger.info(">>> Complete Crypto URI API Address: " + queryUrl);

            RequestEntity<Void> request = RequestEntity.get(queryUrl).build();

            RestTemplate template = new RestTemplate();
            ResponseEntity<String> response;

            response = template.exchange(request, String.class);

            payload = response.getBody();
            logger.info(">>> payload: added" );


        } catch (Exception ex) {
            logger.info(">>> Error: " + ex.getMessage());
            return Collections.emptyList();
        }

        Reader strReader = new StringReader(payload);

        JsonReader jsonReader = Json.createReader(strReader);
        JsonObject articleResult = jsonReader.readObject();

        List<Article> list = new LinkedList<>();
        JsonArray newsItems = articleResult.getJsonArray("Data");
        for (int j = 0; j < newsItems.size(); j++) {
            JsonObject newsItem = newsItems.getJsonObject(j);
            list.add(Article.create(newsItem));
        }
        return list;
    }

    public List<Article> saveArticles(List<Article> articles) {
        for (int i = 0; i < articles.size(); i++) {
            Article art = articles.get(i);
            template.opsForHash().put(HASH_KEY, art.getId(), art);
        }
        logger.info(">>>>articles saved<<<<<");
        return articles;
    }

    public Optional<Article> retrieveArticle(String id) {
        Article art = (Article) template.opsForHash().get(HASH_KEY, id);
        Optional<Article> article = Optional.ofNullable(art);
        logger.info(">>>>articles {} retrieved<<<<<", id);
        return article;
    }
}
