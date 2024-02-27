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

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ChallU {
    @FXML
    private Label DateUser;

    @FXML
    private Label DiscUser;

    @FXML
    private Label HeureUser;

    @FXML
    private Label NomUser;
    @FXML
    private Label ownerU;
    public String Nom;



    @FXML
    private ImageView PhotoUser;
    private Défis d;
    private Afficher afficher;

    public void setd(Défis d) {
        this.d = d;
    }

    public void setDataU(Défis d) {
        NomUser.setText(d.getNom_d());
        DiscUser.setText(d.getDis_d());
        Image image = new Image("file:" + d.getPhoto_d());
        PhotoUser.setImage(image);


        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = d.getDelai().format(dateFormatter);
        DateUser.setText(formattedDate);

        // Format the time
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = d.getHeure().format(timeFormatter);
        HeureUser.setText(formattedTime);
        ownerU.setText(d.getUser().getNom() + " " + d.getUser().getPrenom());



    }

    public void Participer(ActionEvent Event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPart.fxml"));
            Parent root = loader.load();

            AjouterPart ajouterPartController = loader.getController();

            // Assuming 'd' is properly initialized elsewhere in your code
            if (d != null) {
                ajouterPartController.setData(d);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                // Handle the case where 'd' is null
                System.err.println("The Défis object 'd' is null.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }}

