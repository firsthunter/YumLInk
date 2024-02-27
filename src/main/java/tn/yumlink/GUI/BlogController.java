package tn.yumlink.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.yumlink.models.Article;
import tn.yumlink.services.ArticleService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class BlogController implements Initializable {
    @FXML
    Pagination pagination = new Pagination();
    @FXML
    private Button add_post_btn;
    ArticleService articleService = new ArticleService();
    private List<Article> articles;
    private BaseController baseController;

    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            articles = articleService.fetchArticles();
            pagination.setPageCount((int) Math.ceil((double) articles.size() / 9));
            System.out.println((int) Math.ceil((double) articles.size() / 9));
            pagination.setMaxPageIndicatorCount(3);
            pagination.setPageFactory(new Callback<Integer, Node>() {
                @Override
                public Node call(Integer pageIndex) {
                    return getPost_grid(pageIndex);
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private GridPane getPost_grid(int pageIndex){
        GridPane gridPane = new GridPane();
        int columns = 0;
        int rows = 1;
        int max = 0;
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
                postComponent.setArticle(article);
                if (columns == 3) {
                    columns = 0;
                    rows++;
                }
                gridPane.add(vBox, columns++, rows);
                GridPane.setMargin(vBox, new Insets(5,4,0,4));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return gridPane;
    }
    public void setAdd_post_btn(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/CreateNewPost.fxml"));
            Parent createNewPost = fxmlLoader.load();
            Scene createNewPostScene = new Scene(createNewPost);
            Stage stage = (Stage) add_post_btn.getScene().getWindow();
            stage.setScene(createNewPostScene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}