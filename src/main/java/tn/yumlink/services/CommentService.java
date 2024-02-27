package tn.yumlink.services;

import tn.yumlink.models.Article;
import tn.yumlink.models.Comment;
import tn.yumlink.models.User;
import tn.yumlink.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentService implements IServiceComment<Comment> {
    Connection connection;

    public CommentService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void addComment(Comment comment) throws SQLException {

    }

    @Override
    public void modifyComment(Comment comment) throws SQLException {

    }

    @Override
    public void incrementLikes(Comment comment) throws SQLException {

    }

    @Override
    public void deleteComment(int id) throws SQLException {

    }

    @Override
    public List<Comment> fetchComments(int id) throws SQLException {
        String sql = "SELECT c.* , u.nom , u.prenom FROM commentaire c INNER JOIN user u ON c.idU = u.idU WHERE c.id_article = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        List<Comment> commentList = new ArrayList<>();
        while (resultSet.next()) {
            Comment comment = new Comment();
            User user = new User();
            comment.setComment_id(resultSet.getInt("comment_id"));
            user.setIdU(resultSet.getInt("idU"));
            user.setNom(resultSet.getString("nom"));
            user.setPrenom(resultSet.getString("prenom"));
            comment.setUser(user);
            comment.setComment_date(resultSet.getDate("comment_date").toLocalDate());
            comment.setComment_text(resultSet.getString("comment_text"));
            comment.setArticle_id(id);
            commentList.add(comment);
        }
        return commentList;
    }
}
