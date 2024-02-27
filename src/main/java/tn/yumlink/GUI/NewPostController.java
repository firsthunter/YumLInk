package tn.yumlink.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tn.yumlink.models.Article;
import tn.yumlink.services.ArticleService;

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
            try {
                articleService.addArticle(article);
                showSuccessDialog();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });
    }
    private void showSuccessDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Success!");
        dialog.setHeaderText("Form Submitted Successfully");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        // Add a label to the dialog
        Label label = new Label("Your form has been submitted successfully.");
        dialog.getDialogPane().setContent(label);

        // Show the dialog
        dialog.showAndWait();
    }
}
