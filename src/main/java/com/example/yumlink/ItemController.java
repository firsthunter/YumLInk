package com.example.yumlink;

import entities.produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import utils.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemController {

    @FXML
    private Button addP;

    @FXML
    private ImageView fruitImg;

    @FXML
    private Label fruitPriceLabel;
    @FXML
    private Label fruitNameLable;

    @FXML
    private ComboBox<Integer> numberComboBox;

    @FXML
    private Label numserie;
    boolean productAdded = false;

    private produit p;

    // Handle click event

    public void initialize(){
        numserie.setDisable(false);
        ObservableList<Integer> numbers = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        numberComboBox.setItems(numbers);
        Image logoimage = new Image("file:" + "C:/Users/horch/Desktop/YumLink/src/main/java/com/example/yumlink/chariot.png");

//
//
//// Create an ImageView with the icon/image
        ImageView iconImageView = new ImageView(logoimage);
        double iconWidth = 40; // Set the desired width
        double iconHeight = 40; // Set the desired height
        iconImageView.setFitWidth(iconWidth);
        iconImageView.setFitHeight(iconHeight);
        addP.setGraphic(iconImageView);


    }

    @FXML


    public void setData(produit p) {
        numserie.setText(String.valueOf(p.getId()));
        fruitNameLable.setText(p.getNom());
        fruitPriceLabel.setText(String.valueOf(p.getPrix()));
        Image image = new Image("file:" + p.getImage());

        // Set the loaded image to the ImageView
        fruitImg.setImage(image);
    }
@FXML
    void addpanier(ActionEvent event) {
        try {
            String productName = fruitNameLable.getText();
            Integer productqua = numberComboBox.getValue();

            if (productqua == null) {
                productqua = 1;
            }

            int idproduit = Integer.parseInt(numserie.getText());
            int idclient = Integer.parseInt(numserie.getText());
            double productPrice = Double.parseDouble(fruitPriceLabel.getText());

            if (productName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Unable to add product.");
                alert.setContentText("Please enter a valid product name and try again.");
                alert.showAndWait();
            } else {
                try {
                    Connection con = database.getInstance().getConn();
                    String sql = "INSERT INTO panier(quantite, prixtotal, id_produit,id_client) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement st = con.prepareStatement(sql)) {
                        st.setDouble(1, productqua);
                        st.setDouble(2, (productqua*productPrice));
                        st.setInt(3,idproduit );
                        st.setInt(4,idclient );



                        st.executeUpdate();
                    }
                } catch (SQLException e) {
                    // Handle SQL exception appropriately (e.g., log or show user-friendly message)
                    e.printStackTrace();
                    throw new RuntimeException("Error adding product to the database");
                }
                productAdded = true;

                if (productAdded) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success Add");
                    alert.setHeaderText(null);
                    alert.setContentText("Add Panier successfully!");
                    alert.showAndWait();

                }
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to add product.");
            alert.setContentText("Please enter valid numeric values for price and try again.");
            alert.showAndWait();
        }

    }

}
