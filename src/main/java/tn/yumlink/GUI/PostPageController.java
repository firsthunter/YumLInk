package tn.yumlink.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tn.yumlink.models.Article;
import tn.yumlink.models.Comment;
import tn.yumlink.services.CommentService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PostPageController {

    @FXML
    private Label article_content_id;

    @FXML
    private Label author;

    @FXML
    private VBox comment_section;

    @FXML
    private Label date_published_id;

    @FXML
    private Label title_id;

    @FXML
    private Button toggleCommentsButton;
    private Article article;
    CommentService commentService = new CommentService();
    private List<Comment> comments;

    public void setArticle(Article article) {
        this.article = article;
    }

    @FXML
    public void initialize() {
        toggleCommentsButton.setOnAction(event -> {
            fetchCommentsById(article.getId_article());
        });
    }

    public void setArticlePage(Article article) {
        title_id.setText(article.getTitle_article());
        article_content_id.setText(article.getDescription_article());
        author.setText(article.getUser().getPrenom() + " " + article.getUser().getNom());
        String Day = String.valueOf(article.getDate_published().getDayOfMonth());
        String Month = String.valueOf(article.getDate_published().getMonth());
        String MonthSub = Month.substring(0, 3).toLowerCase();
        String Year = String.valueOf(article.getDate_published().getYear());
        String TimeH = String.valueOf(article.getDate_published().getHour());
        String TimeM = String.valueOf(article.getDate_published().getMinute());
        date_published_id.setText("Publie le " + Day + " " + MonthSub + " " + Year + " à " + TimeH + ":" + TimeM);
    }

    public void fetchCommentsById(int article_id) {
        comment_section.visibleProperty().set(false);
        try {
            comments = commentService.fetchComments(article_id);
            updateCommentSection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void updateCommentSection() {
        comment_section.getChildren().clear();
        comment_section.visibleProperty().set(true);
        for (Comment comment : comments) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/comment.fxml"));
                Node commentNode = loader.load();
                CommentController commentController = loader.getController();
                commentController.set_comment(comment);
                comment_section.getChildren().add(commentNode);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
