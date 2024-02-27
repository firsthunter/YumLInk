package tn.yumlink.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.yumlink.models.Article;
import tn.yumlink.services.ArticleService;

import java.io.IOException;
import java.sql.SQLException;

public class PostComponentController {

    public Button supprimer_btn;
    public Button modifier_btn;
    @FXML
    private Label date_published_text;

    @FXML
    private Label time_published_text;

    @FXML
    private ImageView heart_button;

    @FXML
    private Label nb_likes;

    @FXML
    private ImageView post_image;

    @FXML
    private Label post_description;

    @FXML
    private Label post_title;
    @FXML
    private Label author_id;
    private Article article;
    ArticleService articleService = new ArticleService();
    private BaseController baseController;
    private BlogController blogController;

    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    public void setBlogController(BlogController blogController) {
        this.blogController = blogController;
    }

    public void setArticle(Article article) {
        this.article = article;
        set_post_elements(article);
    }

    public void handleDelete() throws SQLException, IOException {
        articleService.deleteArticle(article.getId_article());
        blogController.loadArticles();
    }

    public void handleViewMore() throws IOException {
        FXMLLoader loaderPost = new FXMLLoader(getClass().getResource("/UI/PostPage.fxml"));
        Parent postPageParent = loaderPost.load();
        PostPageController postPageController = loaderPost.getController();
        postPageController.setArticle(article);
        postPageController.setArticlePage(article);
        baseController.getView_content().getChildren().setAll(postPageParent);
    }

    public void set_post_elements(Article article) {
        Image placeholderImage = new Image("/img/placeholder.jpg");
        post_image.setImage(placeholderImage);
        post_title.setText(article.getTitle_article());
        nb_likes.setText(Integer.toString(article.getNb_likes_article()));
        post_description.setText(article.getDescription_article());
        String Day = String.valueOf(article.getDate_published().getDayOfMonth());
        String Month = String.valueOf(article.getDate_published().getMonth());
        String MonthSub = Month.substring(0, 3).toLowerCase();
        String Year = String.valueOf(article.getDate_published().getYear());
        String TimeH = String.valueOf(article.getDate_published().getHour());
        String TimeM = String.valueOf(article.getDate_published().getMinute());
        date_published_text.setText("Publie le " + Day + " " + MonthSub + " " + Year);
        time_published_text.setText(TimeH + ":" + TimeM);
        author_id.setText("Publie par " + article.getUser().getNom() + " " + article.getUser().getPrenom());
    }

    public void HandleUpdate() {
        blogController.update_form.visibleProperty().set(true);
        blogController.update_post_btn.visibleProperty().set(true);
        blogController.title_id_update.setText(article.getTitle_article());
        blogController.img_id_update.setText(article.getImg_article());
        blogController.description_id_update.setText(article.getDescription_article());
        blogController.ArticleToUpdate = article;
    }
}
