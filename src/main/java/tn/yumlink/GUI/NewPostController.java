package tn.yumlink.GUI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import tn.yumlink.models.Article;
import tn.yumlink.models.User;
import tn.yumlink.services.ArticleService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class NewPostController implements Initializable {
    @FXML
    private Button create_post_btn;

    @FXML
    private TextField description_id;

    @FXML
    private TextField img_id;

    @FXML
    private TextField title_id;
    ArticleService articleService = new ArticleService();
    @FXML
    private ImageView photo_Added;
    public String imagePath = "";
    private BaseController baseController;

    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_post_btn.setOnAction(actionEvent -> {
            Article article = new Article();
            LocalDateTime currentDateTime = LocalDateTime.now();
            article.setDate_published(currentDateTime);
            article.setNb_likes_article(0);
            article.setTitle_article(title_id.getText());
            article.setDescription_article(description_id.getText());
            article.setImg_article(img_id.getText());
            User user = new User(6);
            article.setUser(user);
            try {
                articleService.addArticle(article);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
//            ImageView checkmarkImageView = new ImageView(new Image("green_checkmark.png"));
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/Blog.fxml"));
                Parent blogPage = null;
                try {
                    blogPage = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                BlogController blogController = loader.getController();
                blogController.setBaseController(baseController);
                baseController.getView_content().getChildren().clear();
                baseController.getView_content().getChildren().setAll(blogPage);
            }));
            timeline.play();
        });
    }

}

