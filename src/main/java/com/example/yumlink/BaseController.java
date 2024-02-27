package com.example.yumlink;

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
        handleAddFormButtonClick();
        blog_page_btn.setOnAction(actionEvent -> {
            handleAddFormButtonClick();
        });
    }

    @FXML
    private void handleAddFormButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProductForm.fxml"));
            Parent shopPage = loader.load();
            AddProductForm addProductForm = loader.getController();
            addProductForm.setBaseController(this);
            page_content_anchorPane.getChildren().setAll(shopPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void tobasket(javafx.event.ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("panier.fxml"));
            Parent shopPage = loader.load();
            panierController panierController = loader.getController();
            panierController.setBaseController(this);
            page_content_anchorPane.getChildren().setAll(shopPage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void toshop(javafx.event.ActionEvent actionEvent) throws IOException {

        FXMLLoader page = new FXMLLoader(getClass().getResource("shop.fxml"));
        Parent parent = page.load();
        shopController shopController = page.getController();
        shopController.setBaseController(this);
        page_content_anchorPane.getChildren().clear();
        page_content_anchorPane.getChildren().setAll(parent);

    }
}
