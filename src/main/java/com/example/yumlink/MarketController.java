package com.example.yumlink;

import entities.produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MarketController {
    @FXML
    private VBox chosenFruitCard;

    @FXML
    private Label fruitNameLable;
    @FXML
    public TextField mainPartSearchTxt;


    private static int i;

    @FXML
    private Label fruitPriceLabel;

    @FXML
    private ImageView fruitImg;
    private Image image;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;


    public TableView<produit> tableView;

    // existing fields...



    @FXML
    public void initialize() {
        try {
            // Assuming you have a method to get the database connection
            Connection con = database.getInstance().getConn();

            String verify = "SELECT * FROM produit";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(verify);

            // Create an ObservableList to hold your produit objects
            ObservableList<produit> productList = FXCollections.observableArrayList();

            // Assuming produit has fields like "nom", "prix", etc.
            while (rs.next()) {
                String nom = rs.getString("nom");
                float prix = rs.getFloat("prix");
                String description = rs.getString("diescription");
                String image = rs.getString("image");
                // Create a produit object and add it to the list
                produit product = new produit(nom, prix, description, image);
                productList.add(product);
            }

            // Initialize the tableView
            tableView.getColumns().clear();
            tableView.setItems(productList);
            for (produit p : productList) {
                System.out.println("Produit: " + p.getNom() + ", " + p.getPrix());
            }

            TableColumn<produit, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

            TableColumn<produit, Float> priceColumn = new TableColumn<>("Price");
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));

            // Assuming you have other columns for description and image
            TableColumn<produit, String> descriptionColumn = new TableColumn<>("Description");
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("diescription"));

            TableColumn<produit, String> imageColumn = new TableColumn<>("Image");
            imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
            imageColumn.setCellFactory(column -> new ImageTableCell());

            tableView.getColumns().addAll(nameColumn, priceColumn, descriptionColumn, imageColumn);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately
        }
    }
    @FXML
    private Button add_button;
    public void add_buttonOnAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AddProductForm.fxml"));
            Stage stage = (Stage) add_button.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Handle the exception appropriately (e.g., display an error message)
            e.printStackTrace();
        }
    }
}
