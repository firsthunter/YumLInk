package tn.yumlink.services;

import java.sql.SQLException;
import java.util.List;

public interface IServiceComment<T> {
    void addComment(T t) throws SQLException;
    void modifyComment(T t) throws SQLException;
    void incrementLikes(T t) throws SQLException;
    void deleteComment(int id) throws SQLException;
    List<T> fetchComments(int id) throws SQLException;
}
