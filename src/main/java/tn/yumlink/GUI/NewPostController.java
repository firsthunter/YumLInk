package tn.yumlink.GUI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;
import tn.yumlink.models.Article;
import tn.yumlink.models.Tag;
import tn.yumlink.models.User;
import tn.yumlink.services.ArticleService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewPostController implements Initializable {
    @FXML
    private Button create_post_btn;

    @FXML
    private TextArea description_id;

    @FXML
    private TextField img_id;

    @FXML
    private TextField title_id;
    ArticleService articleService = new ArticleService();
    @FXML
    private VBox tagContainer;
    @FXML
    private VBox suggestionContainer;
    private BaseController baseController;
    List<Tag> suggestedTags = new ArrayList<>();

    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        suggestedTags = articleService.fetchTags();
        description_id.textProperty().addListener(((observableValue, s, t1) -> {
            handleTextChanges(t1);
        }));
        create_post_btn.setOnAction(actionEvent -> {
            if (verifyText(title_id.getText(),description_id.getText(),img_id.getText())){
                Article article = new Article();
                LocalDateTime currentDateTime = LocalDateTime.now();
                article.setDate_published(currentDateTime);
                article.setNb_likes_article(0);
                article.setTitle_article(title_id.getText());
                article.setDescription_article(description_id.getText());
                article.setImg_article(img_id.getText());
                article.setTags(StoreAssociatedTags());
                User user = new User(6);
                article.setUser(user);
                try {
                    articleService.addArticle(article);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
//          ImageView checkmarkImageView = new ImageView(new Image("green_checkmark.png"));
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
            }
        });
    }
    private void handleTextChanges(String newText) {
        suggestionContainer.getChildren().clear();
        if (newText.contains("#")) {
            String typedText = newText.substring(newText.lastIndexOf('#') + 1);
            for (Tag tag : suggestedTags) {
                if (tag.getTag_value().startsWith(typedText)) {
                    suggestionContainer.getChildren().add(new Label(tag.getTag_value()));
                }
            }
        }
        tagContainer.getChildren().clear();
        String[] words = newText.split("\\s+");
        for (String word : words) {
            if (word.startsWith("#")) {
                tagContainer.getChildren().add(new Label(word));
            }
        }
    }
    private boolean verifyText(String title, String description, String image){
        title_id.setStyle("");
        img_id.setStyle("");
        description_id.setStyle("");
        if (title.isEmpty()) {
            title_id.setStyle("-fx-border-color: red;");
            return false;
        }
        if (image.isEmpty()) {
            img_id.setStyle("-fx-border-color: red;");
            return false;
        }
        if (description.isEmpty()) {
            description_id.setStyle("-fx-border-color: red;");
            return false;
        }
        return true;
    }
    private JSONObject StoreAssociatedTags() {
        JSONArray tagsJsonArray = new JSONArray();
        for (Node node : tagContainer.getChildren()) {
            if (node instanceof Label label) {
                String tag = label.getText().substring(1);
                tagsJsonArray.put(tag);
            }
        }
        JSONObject tagsJsonObject = new JSONObject();
        tagsJsonObject.put("tags", tagsJsonArray);
        return tagsJsonObject;
    }
}

