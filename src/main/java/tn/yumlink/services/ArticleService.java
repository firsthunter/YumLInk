package tn.yumlink.services;

import tn.yumlink.models.Article;
import tn.yumlink.models.Tag;
import tn.yumlink.models.User;
import tn.yumlink.utils.MyDatabase;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArticleService implements IServiceArticle<Article> {
    Connection connection;

    public ArticleService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void addArticle(Article article) throws SQLException {
        Statement ste = connection.createStatement();
        String tagsJsonString = article.getTags().toString();
        System.out.println(tagsJsonString);
        String req = "INSERT INTO article " +
                "(idU, title_article, img_article, description_article, nb_likes_article, date_published, tags) "
                +
                "VALUES('" + article.getUser().getIdU()
                + "','" + article.getTitle_article()
                + "','" + article.getImg_article()
                + "','" + article.getDescription_article()
                + "'," + article.getNb_likes_article() + ",'"
                + article.getDate_published() + "','"
                + tagsJsonString + "')";
        ste.executeUpdate(req);
    }

    @Override
    public void modifyArticle(Article article) throws SQLException {
        String sql = "UPDATE article SET title_article = ?,img_article = ?, description_article = ? , date_published = ? WHERE id_article = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, article.getTitle_article());
        preparedStatement.setString(2, article.getImg_article());
        preparedStatement.setString(3, article.getDescription_article());
        preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
        preparedStatement.setInt(5, article.getId_article());
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
        String req = "DELETE FROM article WHERE id_article = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Article> fetchArticles() {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT a.*, u.idU AS user_id, u.nom AS nom_user, u.prenom AS prenom_user " +
                "FROM article a " +
                "INNER JOIN user u ON a.idU = u.idU";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {

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
                    articles.add(article);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public List<User> fetchAuthors(){
        List<User> author_chefs = new ArrayList<>();
        String query = "SELECT DISTINCT a.idU, u.idU AS user_id, u.nom AS user_nom, u.prenom AS user_prenom " +
                "FROM article a " +
                "INNER JOIN user u ON a.idU = u.idU";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User chef = new User(resultSet.getInt("user_id"),
                            resultSet.getString("user_nom"),
                            resultSet.getString("user_prenom"));
                    author_chefs.add(chef);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return author_chefs;
    }

    @Override
    public List<Article> fetchArticlesByTags(List<String> tags) {
        List<Article> articles = new ArrayList<>();
        String query = "SELECT a.*, u.idU AS user_id, u.nom AS nom_user, u.prenom AS prenom_user " +
                "FROM article a " +
                "INNER JOIN user u ON a.idU = u.idU " +
                "WHERE JSON_EXTRACT(tags, '$.tags') LIKE ?";
        Set<Integer> uniqueArticleIds = new HashSet<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (String tag : tags) {
                preparedStatement.setString(1, "%\"" + tag + "\"%");
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int articleId = resultSet.getInt("id_article");
                        if (!uniqueArticleIds.contains(articleId)) {
                            Article article = new Article();
                            article.setId_article(articleId);
                            article.setImg_article(resultSet.getString("img_article"));
                            article.setDescription_article(resultSet.getString("description_article"));
                            article.setTitle_article(resultSet.getString("title_article"));
                            article.setNb_likes_article(resultSet.getInt("nb_likes_article"));
                            article.setDate_published(resultSet.getTimestamp("date_published").toLocalDateTime());
                            User user = new User(resultSet.getInt("user_id"), resultSet.getString("nom_user"), resultSet.getString("prenom_user"));
                            article.setUser(user);
                            articles.add(article);
                            uniqueArticleIds.add(articleId);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public List<Tag> fetchTags() {
        List<Tag> tags = new ArrayList<>();
        String sql = "SELECT * FROM tag";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Tag tag = new Tag();
                    tag.setTag_id(resultSet.getInt("tag_id"));
                    tag.setTag_value(resultSet.getString("tag_value"));
                    tag.setTag_nb_usage(resultSet.getInt("tag_nb_usage"));
                    tags.add(tag);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tags;
    }
}
