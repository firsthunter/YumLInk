package com.example.yumlink;

/***
 * Created by Breanna Olden
 * Student ID: 001532324
 * C482 Software 1
 *
 */



import entities.produit;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import utils.database;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProductForm {


    @FXML
    public Button cancelAddProductButton;

    @FXML
    public TextField productPriceTxt;
    @FXML
    public TextField descp;
    @FXML
    private TextField idonlyread;
    @FXML
    public TextField nomp;
    @FXML
    public TextField imagep;
    @FXML
    public TextField addProductSearchTxt;

    public static int productIdNum;
    @FXML
    public TableView partsTable;

    @FXML
    public Button addProductSaveButton;
    @FXML
    public Button addProductRemoveAssociatedPartButton;
    @FXML
    private Button btntosop;

    int productId = productIdNum;

    boolean productAdded = false;

    private ObservableList<produit> associatedPart = FXCollections.observableArrayList();
    BaseController baseController;

    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    /***
     * @param actionEvent
     * Function: Allows for addition of product to productTable.
     * Parses entry from textfield to the appropriate data type.
     *
     * Produces error if no product name entered.
     * Produces error if inventory parameters are invalid.
     * Produces error if other textfields left blank or with invalid
     * data types/entry.
     *
     * If product successfully added, navigates user back to home screen.
     *
     * RUNTIME ERROR
     * Logic error: stipulated that productstock >= productMax rather than <= productMax.
     * Would kick back error print in console.
     * @throws IOException
     * FUTURE ENHANCEMENT
     * Figure out how to auto increment id
     */
    public void addProductSaveClick(ActionEvent actionEvent) {
        try {
            String productName = nomp.getText();
            String image = imagep.getText();
            double productPrice = Double.parseDouble(productPriceTxt.getText());
            String description = descp.getText();

            if (productName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Unable to add product.");
                alert.setContentText("Please enter a valid product name and try again.");
                alert.showAndWait();
            } else {
                try {
                    Connection con = database.getInstance().getConn();
                    String sql = "INSERT INTO produit(nom, prix, diescription, image) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement st = con.prepareStatement(sql)) {
                        st.setString(1, productName);
                        st.setDouble(2, productPrice);
                        st.setString(3, description);
                        st.setString(4, image);

                        st.executeUpdate();
                    }
                } catch (SQLException e) {
                    // Handle SQL exception appropriately (e.g., log or show user-friendly message)
                    e.printStackTrace();
                    throw new RuntimeException("Error adding product to the database");
                }

                productIdNum++;
                productAdded = true;

                if (productAdded) {
                    Parent root = FXMLLoader.load(getClass().getResource("AddProductForm.fxml"));
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    stage.setTitle("shop Screen");
                    stage.setScene(new Scene(root));
                    stage.show();
                }
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to add product.");
            alert.setContentText("Please enter valid numeric values for price and try again.");
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /***
     * @param actionEvent
     * When cancel button is clicked, user is prompted to confirm
     * their desire to cancel this action. If user clicks "OK"
     * they are sent back to the home screen
     * @throws IOException
     */
    public void cancelAddProductClick(ActionEvent actionEvent) throws IOException {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Cancel Action");
        confirm.setHeaderText("Are you sure you would like to cancel?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            Parent root = FXMLLoader.load(getClass().getResource("/view/FirstScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Home Screen");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }



    public void addProductSearchClick(ActionEvent actionEvent) {
    }


    /***
     *
     * Auto populates tables.
     */

    public void initialize() {

        idonlyread.setEditable(false);
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
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                float prix = rs.getFloat("prix");
                String description = rs.getString("diescription");
                String image = rs.getString("image");
                // Create a produit object and add it to the list
                produit product = new produit(id, nom, prix, description, image);
                productList.add(product);
            }

            // Initialize the tableView
            partsTable.getColumns().clear();
            partsTable.setItems(productList);
            TableColumn<produit, String> idColumn = new TableColumn<>("id");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<produit, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

            TableColumn<produit, Float> priceColumn = new TableColumn<>("Price");
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));

            // Assuming you have other columns for description and image
            TableColumn<produit, String> descriptionColumn = new TableColumn<>("Description");
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("diescription"));

            TableColumn<produit, String> imageColumn = new TableColumn<>("Image");
            imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));


            partsTable.getColumns().addAll(idColumn, nameColumn, priceColumn, descriptionColumn, imageColumn);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately
        }
        partsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<produit>() {
            @Override
            public void changed(ObservableValue<? extends produit> observable, produit oldValue, produit newValue) {
                if (newValue != null) {
                    // Handle the selected product
                    System.out.println(newValue.getNom());
                    // Update labels with selected data
                    updateLabels(newValue);
                }
            }
        });

    }

    @FXML
    void addphoto(ActionEvent event) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        // Show the file chooser dialog
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // You can handle the selected file here
            String imagePath = selectedFile.toURI().toString();
            if (imagePath.startsWith("file:/")) {
                imagePath = imagePath.substring(6);
            }

            // Display the modified image path in the label
            imagep.setText(imagePath);

            // Perform any additional operations with the image path


            // You can also save the image path to a database or perform other operations
        } else {
            // User canceled the file selection
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Image Selection Cancelled");
            alert.setContentText("No image selected.");
            alert.showAndWait();
        }
    }


    @FXML
    private TableView<produit> tableView;

    // Method to update labels with selected data

    private void updateLabels(produit selectedProduct) {
        idonlyread.setText(String.valueOf(selectedProduct.getId()));
        nomp.setText(selectedProduct.getNom());
        productPriceTxt.setText(String.valueOf(selectedProduct.getPrix()));
        descp.setText(selectedProduct.getDiescription());
        imagep.setText(selectedProduct.getImage());
    }


    @FXML
    void updateproduit(ActionEvent event) {
        String productName = nomp.getText();
        String image = imagep.getText();
        double productPrice = Double.parseDouble(productPriceTxt.getText());
        String description = descp.getText();
        int idp = Integer.parseInt(idonlyread.getText());
        try {
            Connection con = database.getInstance().getConn();
            // Assuming you have an appropriate UPDATE SQL statement
            String updateSQL = "UPDATE produit SET nom=?, prix=?, diescription=?, image=? WHERE id=?";
            try (PreparedStatement st = con.prepareStatement(updateSQL)) {
                // Set the updated values
                st.setString(1, productName);
                st.setDouble(2, productPrice);
                st.setString(3, description);
                st.setString(4, image);
                st.setInt(5, idp);

                // Execute the update statement
                st.executeUpdate();


                Parent root = FXMLLoader.load(getClass().getResource("AddProductForm.fxml"));
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setTitle("admin Screen");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately
        }


    }

    @FXML
    void removeproduit(ActionEvent event) {
        int idp = Integer.parseInt(idonlyread.getText());
        try {
            Connection con = database.getInstance().getConn();
            // Assuming you have an appropriate DELETE SQL statement
            String deleteSQL = "DELETE FROM produit WHERE id=?";
            try (PreparedStatement st = con.prepareStatement(deleteSQL)) {
                st.setInt(1, idp);  // Set the correct parameter index (1, not 5)
                st.executeUpdate();
                Parent root = FXMLLoader.load(getClass().getResource("AddProductForm.fxml"));
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setTitle("admin Screen");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately
        }
        partsTable.refresh();

    }
    @FXML
    void toshop(ActionEvent event) throws IOException {

        FXMLLoader page = new FXMLLoader(getClass().getResource("shop.fxml"));
        Parent parent = page.load();

        shopController shopController = page.getController();
        shopController.setBaseController(baseController);
        baseController.getView_content().getChildren().setAll(parent);
    }
}