package tn.yumlink.models;

import org.json.JSONObject;

import java.time.LocalDateTime;

public class Article {
    private int id_article;
    private User user;
    private String title_article;
    private String img_article;
    private String description_article;
    private int nb_likes_article;
    private LocalDateTime date_published;
    private JSONObject tags;

    public Article() {
    }

    public Article(int id_article, User user, String title_article, String img_article, String description_article, int nb_likes_article, LocalDateTime date_published, JSONObject tags) {
        this.id_article = id_article;
        this.user = user;
        this.title_article = title_article;
        this.img_article = img_article;
        this.description_article = description_article;
        this.nb_likes_article = nb_likes_article;
        this.date_published = date_published;
        this.tags = tags;
    }

    public int getId_article() {
        return id_article;
    }

    public void setId_article(int id_article) {
        this.id_article = id_article;
    }

    public String getTitle_article() {
        return title_article;
    }

    public void setTitle_article(String title_article) {
        this.title_article = title_article;
    }

    public int getNb_likes_article() {
        return nb_likes_article;
    }

    public void setNb_likes_article(int nb_likes_article) {
        this.nb_likes_article = nb_likes_article;
    }

    public String getImg_article() {
        return img_article;
    }

    public void setImg_article(String img_article) {
        this.img_article = img_article;
    }

    public String getDescription_article() {
        return description_article;
    }

    public void setDescription_article(String description_article) {
        this.description_article = description_article;
    }

    public LocalDateTime getDate_published() {
        return date_published;
    }

    public void setDate_published(LocalDateTime date_published) {
        this.date_published = date_published;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JSONObject getTags() {
        return tags;
    }

    public void setTags(JSONObject tags) {
        this.tags = tags;
    }
}

