package org.example;

import entities.Défis;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.DéfisS;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;


public class Chall {

    @FXML
    private Label DateC;

    @FXML
    private Label HeureC;

    @FXML
    private Label disC;

    @FXML
    private Label nomC;

    @FXML
    private ImageView photoC;
    @FXML
    private Label Owner;
    private Défis d;
    private Afficher afficher;

    public void setD(Défis d) {
        this.d = d;
    }

    // Handle click event

    public void initialize() {


    }

    @FXML


    public void setData(Défis d) {

        nomC.setText(d.getNom_d());
        disC.setText(d.getDis_d());
        Image image = new Image("file:" + d.getPhoto_d());
        photoC.setImage(image);



        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = d.getDelai().format(dateFormatter);
        DateC.setText(formattedDate);

        // Format the time
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = d.getHeure().format(timeFormatter);
        HeureC.setText(formattedTime);
        Owner.setText(d.getUser().getNom() + " " + d.getUser().getPrenom());

        // Set the loaded image to the ImageView


    }

    @FXML

    public void supp(ActionEvent actionEvent) {

        if (d != null) {
            try {
                DéfisS DS = new DéfisS();
                DS.supprimer(d.getId_d());
                afficher.refreshView();
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle delete error
            }

        }



    }

    public void modi(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/update.fxml"));
            Parent root = loader.load();

            Update updateController = loader.getController();
            updateController.initData(d, afficher);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle update view loading error
        }
    }

    public void setRefresh(Afficher afficher) {
        this.afficher = afficher;
    }
}


