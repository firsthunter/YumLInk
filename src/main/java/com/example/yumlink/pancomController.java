package com.example.yumlink;

import entities.panier;
import entities.produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utils.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class pancomController {

    @FXML
    private Button butd;

    @FXML
    private TextField name;

    @FXML
    private ImageView pimage;

    @FXML
    private TextField prix;

    @FXML
    private TextField quantite;
    @FXML
    private TextField idonlyread;

    @FXML
    private void initialize() {

        Image iconImage = new Image(new File("C:/Users/horch/Desktop/YumLink/src/main/java/com/example/yumlink/clair.png").toURI().toString());

        ImageView iconImageView = new ImageView(iconImage);
        double iconWidth = 40; // Set the desired width
        double iconHeight = 40; // Set the desired height
        iconImageView.setFitWidth(iconWidth);
        iconImageView.setFitHeight(iconHeight);
        butd.setGraphic(iconImageView);
    }

    public void setData(produit p) throws SQLException {
        Connection con = database.getInstance().getConn();

        // Assuming you have a method to get the database connection

        PreparedStatement st = con.prepareStatement(
                "SELECT * FROM panier ");

        ;
        try (ResultSet rs = st.executeQuery()) {
            // Create an ObservableList to hold your produit objects
            ObservableList<panier> paniertList = FXCollections.observableArrayList();

            // Assuming produit has fields like "nom", "prix", etc.
            while (rs.next()) {
                int idp = rs.getInt("idp");
                int quantite = rs.getInt("quantite");
                float prixT = rs.getFloat("prixtotal");
                int id_client = rs.getInt("id_client");
                int id_produit = rs.getInt("id_produit");

                // Create a produit object and add it to the list
                panier ppp = new panier(idp, quantite, prixT, id_produit, id_client);
                paniertList.add(ppp);
            }
            for (panier pp : paniertList) {
                if(p.getId()==pp.getId_prduit()) {
                    quantite.setText(String.valueOf(pp.getQuantite()));
                    name.setText(p.getNom());
                    prix.setText(String.valueOf(pp.getPrixtotal()));
                    Image image = new Image("file:" + p.getImage());
                    idonlyread.setText(String.valueOf(p.getId()));
                    // Set the loaded image to the ImageView
                    pimage.setImage(image);
                }
            }
        }
    }

    @FXML
    void removeproduit(ActionEvent event) {
        int idp = Integer.parseInt(idonlyread.getText());
        try (Connection con = database.getInstance().getConn()) {
            if (con != null && !con.isClosed()) {
                System.out.println("Connection is open.");

                String deleteSQL = "DELETE FROM panier WHERE idp = ?";
                try (PreparedStatement st = con.prepareStatement(deleteSQL)) {
                    st.setInt(1, idp);
                    int rowsAffected = st.executeUpdate();
                    System.out.println(rowsAffected + " row(s) deleted.");
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle SQLException appropriately
                }
            } else {
                System.out.println("Connection is closed or null.");
            }

            // Load the updated UI (if necessary)
            try {
                Parent root = FXMLLoader.load(getClass().getResource("panier.fxml"));
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







}

