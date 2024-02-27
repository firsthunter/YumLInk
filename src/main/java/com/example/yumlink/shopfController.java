package com.example.yumlink;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import java.net.URL;


import entities.produit;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.database;

/**
 * @author SFernando
 */
public class shopfController {

    private static final String DECORATION_BUTTON_RESTORE = "decoration-button-restore";



    private Stage stage;

    private Rectangle2D rec2;

    private Double width;

    private Double height;

    @FXML
    private Button maximize;

    @FXML
    private Button minimize;

    @FXML
    private Button resize;

    @FXML
    private Button homeb;
    @FXML
    private Button homeb1;
    @FXML
    private Button homeb11;
    @FXML
    private GridPane mainGridPane;
    @FXML
    private TableView<produit> tableView;
    @FXML
    private ImageView LOGOImg;


    public void initialize()
    {


        Image iconImage = new Image(new File("C:/Users/horch/Desktop/YumLink/src/main/java/com/example/yumlink/panier.png").toURI().toString());
        Image iconImage1= new Image(new File("C:/Users/horch/Desktop/YumLink/src/main/java/com/example/yumlink/utilisateur.png").toURI().toString());
//        Image iconImage2= new Image(new File("C:/Users/horch/Desktop/YumLink/src/main/java/com/example/yumlink/market.png").toURI().toString());
        Image logoimage = new Image("file:" + "C:/Users/horch/Desktop/YumLink/src/main/java/com/example/yumlink/logof.png");
        LOGOImg.setImage(logoimage);
//
//
//// Create an ImageView with the icon/image
        ImageView iconImageView = new ImageView(iconImage);
        ImageView iconImageView1 = new ImageView(iconImage1);
//        ImageView iconImageView2 = new ImageView(iconImage2);
//        // Set the fit width and fit height to resize the icon
        double iconWidth = 40; // Set the desired width
       double iconHeight = 40; // Set the desired height
        iconImageView.setFitWidth(iconWidth);
      iconImageView.setFitHeight(iconHeight);
        iconImageView1.setFitWidth(iconWidth);
        iconImageView1.setFitHeight(iconHeight);
//        iconImageView2.setFitWidth(iconWidth);
//        iconImageView2.setFitHeight(iconHeight);
//
//// Set the graphic content of the button to the ImageView
        homeb.setGraphic(iconImageView);
//        homeb.setGraphic(iconImageView);
        homeb1.setGraphic(iconImageView1);
//        homeb11.setGraphic(iconImageView2);
//
//// Set the action for the button
//        homeb1.setOnAction(e -> {
//            try {
//                Parent root = FXMLLoader.load(getClass().getResource("panier.fxml"));
//                Stage stage = (Stage) homeb1.getScene().getWindow();
//                Scene scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//        });
//        homeb.setOnAction(e -> {
//            try {
//                Parent root = FXMLLoader.load(getClass().getResource("AddProductForm.fxml"));
//                Stage stage = (Stage) homeb.getScene().getWindow();
//                Scene scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//        });

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
            mainGridPane.getChildren().clear();
            int row = 0;
            int col = 0;

            for (produit product : productList) {
                col++;
                FXMLLoader itemLoader = new FXMLLoader(getClass().getResource("item.fxml"));
                Parent interfaceRoot = itemLoader.load();
                ItemController itemController = itemLoader.getController();
                itemController.setData(product);

                mainGridPane.add(interfaceRoot, col, row);
                mainGridPane.setHgap(80); // Set the horizontal gap between items
                mainGridPane.setVgap(40); // Set the vertical gap between items


                if (col == 3) { // Change this value based on your layout
                    col = 0;
                    row++;
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
    private void updateLabels(produit selectedProduct) {

    }


    @FXML
    private void maximize()
    {


        stage = (Stage) maximize.getScene().getWindow();
        if (stage.isMaximized())
        {
            if (width == rec2.getWidth() && height == rec2.getHeight())
            {
                stage.setMaximized(false);
                stage.setHeight(600);
                stage.setWidth(800);
                stage.centerOnScreen();
                maximize.getStyleClass().remove(DECORATION_BUTTON_RESTORE);
                resize.setVisible(true);
            }
            else
            {
                stage.setMaximized(false);
                maximize.getStyleClass().remove(DECORATION_BUTTON_RESTORE);
                resize.setVisible(true);
            }

        }
        else
        {
            stage.setMaximized(true);
            stage.setHeight(rec2.getHeight());
            maximize.getStyleClass().add(DECORATION_BUTTON_RESTORE);
            resize.setVisible(false);
        }
    }

    @FXML
    private void minimize()
    {
        stage = (Stage) minimize.getScene().getWindow();
        if (stage.isMaximized())
        {
            width = rec2.getWidth();
            height = rec2.getHeight();
            stage.setMaximized(false);
            stage.setHeight(height);
            stage.setWidth(width);
            stage.centerOnScreen();
            Platform.runLater(() -> {
                stage.setIconified(true);
            });
        }
        else
        {
            stage.setIconified(true);
        }
    }


    @FXML
    private void close()
    {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void tobasket(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("panier.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setTitle("admin Screen");
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    private void logout()
    {
        // ObjectInputFilter.Config config = new ObjectInputFilter.Config();
        // config.newStage(btnLogout, "/sand/view/Login.fxml", "Sample Apps", true, StageStyle.UNDECORATED, false);
    }

}
