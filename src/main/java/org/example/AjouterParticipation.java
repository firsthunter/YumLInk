package org.example;

import entities.Défis;
import entities.Participant;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

public class AjouterParticipation {

    @FXML
    private Label DefiSelected;

    @FXML
    private ImageView participa;
    public String imagePart ="";
    private Défis d;

    @FXML
    private Button ajoutP;

    public void setData(Défis d) {
        this.d = d;
        setField();
    }

    private void setField() {

        if (DefiSelected != null && d != null) {
            DefiSelected.setText(d.getNom_d());
        } else {

            System.err.println("DefiSelected or e is null");
        }
    }

    @FXML
   public void AjouterPar(ActionEvent event) {
        if ( imagePart.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez sélectionner une image. ");
            alert.show();
            return; // Exit method if any field is empty
        }
        User user = new User(5);
         int vote =0;
        Participant par = new Participant(imagePart,user,d,vote);
        ParticipantS parS = new ParticipantS();
        try {
            parS.ajouterP(par);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherParticipation.fxml"));
            Stage stage = (Stage) ajoutP.getScene().getWindow();
            stage.close();

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


