package org.example;

import entities.Défis;
import entities.Participant;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ParticipantS;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AjouterPart {

    @FXML
    private Label DefiSelected;

    @FXML
    private ImageView participa;
    public String imagePart ="";
    @FXML
    private Button ajoutP;
    private Défis d;



    public void setData(Défis d) {
        this.d = d;
        setField();
    }

    private void setField() {
        // Make sure DefiSelected is properly initialized before setting its text
        if (DefiSelected != null && d != null) {
            DefiSelected.setText(d.getNom_d());
        } else {
            // Handle the case where DefiSelected or e is null
            // For example, you can log an error message or throw an exception
            System.err.println("DefiSelected or e is null");
        }
    }

    @FXML
   public void AjouterPar(ActionEvent event) {
        User user = new User(5);

        Participant par = new Participant(imagePart,user,d);

        ParticipantS parS = new ParticipantS();


        try {
            parS.ajouterP(par);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherPartic.fxml"));
            Stage stage = (Stage) ajoutP.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


    }

    @FXML
   public void Participation(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String imagePath = file.toURI().toString();
            System.out.println("Image Path: " + imagePath); // Debugging: Print out image path

            try {
                Image image = new Image(imagePath);
                participa.setImage(image);
                // Set the image to the ImageView
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error loading image: " + e.getMessage());
                alert.show();
            }

        }
        imagePart = file.getAbsolutePath();
    }



}


