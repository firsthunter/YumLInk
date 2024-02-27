package tn.yumlink.services;

import tn.yumlink.models.Article;
import tn.yumlink.models.User;
import tn.yumlink.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleService implements IServiceArticle<Article> {
    Connection connection;

    public ArticleService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void addArticle(Article article) throws SQLException {
        Statement ste = connection.createStatement();
        String req = "INSERT INTO article (title_article, img_article, description_article, nb_likes_article, date_published) "
                + "VALUES('" + article.getTitle_article() + "','" + article.getImg_article() + "','" + article.getDescription_article() + "'," + article.getNb_likes_article() + ",'" + article.getDate_published() + "')";
        ste.executeUpdate(req);
    }

    @Override
    public void modifyArticle(Article article) throws SQLException {
        String sql = "UPDATE article SET title_article = ?,img_article = ?, description_article = ? WHERE id_article = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, article.getTitle_article());
        preparedStatement.setString(2, article.getImg_article());
        preparedStatement.setString(3, article.getDescription_article());
        preparedStatement.setInt(4, article.getId_article());
        preparedStatement.executeUpdate();
    }

    @Override
    public void incrementLikes(Article article) throws SQLException {
        String sql = "UPDATE article SET nb_likes_article = ? WHERE id_article = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, article.getNb_likes_article() + 1);
        preparedStatement.setInt(2, article.getId_article());
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteArticle(int id) throws SQLException {

    }

    @Override
    public List<Article> fetchArticles() throws SQLException {
        String sql = "SELECT a.*, u.idU AS user_id, u.nom AS nom_user, u.prenom AS prenom_user " +
                "FROM article a " +
                "INNER JOIN user u ON a.idU = u.idU";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<Article> list = new ArrayList<>();
        while (resultSet.next()) {
            Article article = new Article();
            article.setId_article(resultSet.getInt("id_article"));
            article.setImg_article(resultSet.getString("img_article"));
            article.setDescription_article(resultSet.getString("description_article"));
            article.setTitle_article(resultSet.getString("title_article"));
            article.setNb_likes_article(resultSet.getInt("nb_likes_article"));
            article.setDate_published(resultSet.getTimestamp("date_published").toLocalDateTime());
            User user = new User(resultSet.getInt("user_id"), resultSet.getString("nom_user"), resultSet.getString("prenom_user"));
            article.setUser(user);
            list.add(article);
        }
        return list;
    }
}
