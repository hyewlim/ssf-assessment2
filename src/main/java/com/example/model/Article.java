package com.example.model;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Article")
public class Article implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(Article.class);
    private String id;
    private Integer published_on;
    private String title;
    private String url;
    private String imageurl;
    private String body;
    private String tags;
    private String categories;

    public Article() {
    }

    public Article(String id, Integer published_on, String title, String url, String imageurl, String body, String tags, String categories) {
        this.id = id;
        this.published_on = published_on;
        this.title = title;
        this.url = url;
        this.imageurl = imageurl;
        this.body = body;
        this.tags = tags;
        this.categories = categories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPublished_on() {
        return published_on;
    }

    public void setPublished_on(Integer published_on) {
        this.published_on = published_on;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", published_on=" + published_on +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", body='" + body + '\'' +
                ", tags='" + tags + '\'' +
                ", categories='" + categories + '\'' +
                '}';
    }

    public static Article create(JsonObject jsonObject) {
        logger.info("create article output");
        Article article = new Article();
        article.setId(jsonObject.getString("id"));
        article.setPublished_on(jsonObject.getJsonNumber("published_on").intValue());
        article.setTitle(jsonObject.getString("title"));
        article.setUrl(jsonObject.getString("url"));
        article.setImageurl(jsonObject.getString("imageurl"));
        article.setBody(jsonObject.getString("body"));
        article.setTags(jsonObject.getString("tags"));
        article.setCategories(jsonObject.getString("categories"));
        logger.info("Article output: " + article.toString());
        return article;
    }

    public JsonObject toJson() {
        logger.info("create Json object");
        return Json.createObjectBuilder()
                .add("id", id)
                .add("published_on", published_on)
                .add("title", title)
                .add("url", url)
                .add("imageurl", imageurl)
                .add("body", body)
                .add("tags", tags)
                .add("categories", categories)
                .build();
    }

}
