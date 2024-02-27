package tn.yumlink.services;

import java.sql.SQLException;
import java.util.List;
public interface IServiceArticle<T> {
    void addArticle(T t) throws SQLException;
    void modifyArticle(T t) throws SQLException;
    void incrementLikes(T t) throws SQLException;
    void deleteArticle(int id) throws SQLException;
    List<T> fetchArticles() throws SQLException;
}