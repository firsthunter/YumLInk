package com.example.beta.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BaseController implements Initializable {
    @FXML
    private Button basket_btn;

    @FXML
    private Button blog_page_btn;

    @FXML
    private Button defis_page_btn;

    @FXML
    private ImageView home_logo_img;

    @FXML
    private Button listChef_page_btn;

    @FXML
    private ImageView log_out_btn;

    @FXML
    private Button nutri_page_btn;

    @FXML
    private Button profile_btn;

    @FXML
    private Button recette_page_btn;

    @FXML
    private Button shop_page_btn;
    @FXML
    private AnchorPane page_content_anchorPane;
    public AnchorPane getView_content() {
        return page_content_anchorPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleUserNutButtonClick();
        nutri_page_btn.setOnAction(actionEvent -> {
            handleUserNutButtonClick();
        });
    }

    @FXML
    private void handleUserNutButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/beta/usernutrition_page.fxml"));
            Parent parent = loader.load();
            UsernutritionPage usernutritionPage = loader.getController();
            usernutritionPage.setBaseController(this);
            page_content_anchorPane.getChildren().setAll(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
