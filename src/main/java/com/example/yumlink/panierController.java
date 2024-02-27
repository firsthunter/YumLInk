package com.example.yumlink;

import entities.panier;
import entities.produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import utils.database;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class panierController {


    @FXML
    private TableView<produit> tableData;

    @FXML
    private GridPane teethPane;

    @FXML
    private ComboBox<?> toothComboBoxOne;

    @FXML
    private ComboBox<?> toothComboBoxThree;

    @FXML
    private ComboBox<?> toothComboBoxTwo;

    @FXML
    private TextArea toothTextArea;

    @FXML
    private TextField txtId;

    @FXML
    private GridPane typeOnePane;

    @FXML
    private GridPane typeThreePane;

    @FXML
    private GridPane typeTwoPane;

    @FXML
    private VBox vboxCrud;
    @FXML
    private GridPane mainGridPane;

    BaseController baseController;

    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    @FXML
    private void initialize() {
        Connection con = null;

        try {
            int clientId = 6;  // Replace with your actual client ID
            con = database.getInstance().getConn();

            // Assuming you have a method to get the database connection

                PreparedStatement st = con.prepareStatement(
                        "SELECT produit.* FROM produit " +
                                "JOIN panier ON produit.id = panier.id_produit " +
                                "WHERE panier.id_client = ?");

                st.setInt(1, clientId);
                try (ResultSet rs = st.executeQuery()) {
                    // Create an ObservableList to hold your produit objects
                    ObservableList<produit> productList = FXCollections.observableArrayList();

                    // Assuming produit has fields like "nom", "prix", etc.
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String nom = rs.getString("nom");
                        float prix = rs.getFloat("prix");
                        String description = rs.getString("diescription");
                        String image = rs.getString("image");

                        // Create a produit object and add it to the list
                        produit product = new produit(id, nom, prix, description, image);
                        productList.add(product);
                    }

                    mainGridPane.getChildren().clear();
                    int row = 1;
                    int col = 0;

                    for (produit product : productList) {
                        FXMLLoader itemLoader = new FXMLLoader(getClass().getResource("pancom.fxml"));
                        Parent interfaceRoot = itemLoader.load();
                        pancomController itemController = itemLoader.getController();
                        itemController.setData(product);

                        mainGridPane.add(interfaceRoot, col, row);
                        mainGridPane.setHgap(40); // Set the horizontal gap between items
                        mainGridPane.setVgap(30); // Set the vertical gap between items

                        col++;
                        if (col == 1) { // Change this value based on your layout
                            col = 0;
                            row++;
                        }
                    }
                }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}



