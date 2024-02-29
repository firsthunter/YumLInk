package tn.yumlink.models;

import java.time.LocalDate;

public class Comment {
    private int comment_id;
    private int article_id;
    private User user;
    private String comment_text;
    private LocalDate comment_date;

    public Comment() {
    }

    public Comment(int article_id, User user, String comment_text, LocalDate comment_date) {
        this.article_id = article_id;
        this.user = user;
        this.comment_text = comment_text;
        this.comment_date = comment_date;
    }

    public Comment(int comment_id, int article_id, User user, String comment_text, LocalDate comment_date) {
        this.comment_id = comment_id;
        this.article_id = article_id;
        this.user = user;
        this.comment_text = comment_text;
        this.comment_date = comment_date;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }
    public String getComment_text() {
        return comment_text;
    }
    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public LocalDate getComment_date() {
        return comment_date;
    }

    public void setComment_date(LocalDate comment_date) {
        this.comment_date = comment_date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
