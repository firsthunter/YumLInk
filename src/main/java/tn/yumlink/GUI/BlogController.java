package tn.yumlink.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.SearchableComboBox;
import tn.yumlink.models.Article;
import tn.yumlink.models.Tag;
import tn.yumlink.models.User;
import tn.yumlink.services.ArticleService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BlogController implements Initializable {
    @FXML
    public VBox update_form;
    @FXML
    public TextField title_id_update;
    @FXML
    public TextField img_id_update;
    @FXML
    public TextField description_id_update;
    @FXML
    public Button update_post_btn;

    @FXML
    Pagination pagination = new Pagination();
    @FXML
    CheckComboBox<Tag> tagCheckComboBox = new CheckComboBox<>();
    @FXML
    private SearchableComboBox<User> chefSearchComboBox;
    ArticleService articleService = new ArticleService();
    private List<Article> articles;
    private List<Tag> availableTags = new ArrayList<>();
    private List<User> author_chefs = new ArrayList<>();
    Article ArticleToUpdate = new Article();
    private BaseController baseController;

    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        articles = articleService.fetchArticles();
        author_chefs = articleService.fetchAuthors();
        availableTags = articleService.fetchTags();
        chefSearchComboBox.getItems().addAll(author_chefs);
        tagCheckComboBox.getItems().addAll(availableTags);
        update_form.visibleProperty().set(false);
        update_post_btn.visibleProperty().set(false);
        loadArticles();
        update_post_btn.setOnAction(actionEvent -> {
            handleUpdateVbox();
            availableTags = articleService.fetchTags();
            loadArticles();
            update_form.visibleProperty().set(false);
            update_post_btn.visibleProperty().set(false);
        });
    }

    private GridPane getPost_grid(int pageIndex) {
        GridPane gridPane = new GridPane();
        int columns = 0;
        int rows = 1;
        int max;
        int articlesPerPage = 9;
        int page = pageIndex * articlesPerPage;
        if ((page + articlesPerPage) >= articles.size()) {
            max = articles.size();
        } else {
            max = page + articlesPerPage;
        }
        try {
            for (int i = page; i < max; i++) {
                Article article = articles.get(i);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/UI/Post.fxml"));
                VBox vBox = fxmlLoader.load();
                PostComponentController postComponent = fxmlLoader.getController();
                postComponent.setBaseController(baseController);
                postComponent.setBlogController(this);
                postComponent.setArticle(article);
                if (columns == 3) {
                    columns = 0;
                    rows++;
                }
                gridPane.add(vBox, columns++, rows);
                GridPane.setMargin(vBox, new Insets(5, 4, 0, 4));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return gridPane;
    }

    public void setAdd_post_btn() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/CreateNewPost.fxml"));
            Parent createNewPost = fxmlLoader.load();
            NewPostController newPostController = fxmlLoader.getController();
            newPostController.setBaseController(baseController);
            baseController.getView_content().getChildren().setAll(createNewPost);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadArticles() {
        pagination.setPageCount((int) Math.ceil((double) this.articles.size() / 9));
        pagination.setMaxPageIndicatorCount(3);
        pagination.setPageFactory(this::getPost_grid);
    }

    public void handleUpdateVbox() {
        String Newtitle = title_id_update.getText();
        String NewImg = img_id_update.getText();
        String NewDescription = description_id_update.getText();
        ArticleToUpdate.setTitle_article(Newtitle);
        ArticleToUpdate.setImg_article(NewImg);
        ArticleToUpdate.setDescription_article(NewDescription);
        try {
            articleService.modifyArticle(ArticleToUpdate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleFilterByTags() {
        List<Tag> selectedTags = tagCheckComboBox.getCheckModel().getCheckedItems();
        List<String> selectedTagValues = new ArrayList<>();
        System.out.println(selectedTags);
        if (selectedTags.isEmpty()) {
            this.articles = articleService.fetchArticles();
        } else {
            for (Tag tag : selectedTags) {
                selectedTagValues.add(tag.getTag_value());
            }
            this.articles = articleService.fetchArticlesByTags(selectedTagValues);
        }
        loadArticles();
    }
}