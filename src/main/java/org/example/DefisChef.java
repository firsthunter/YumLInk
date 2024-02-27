package org.example;

import entities.Défis;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jfxtras.scene.control.LocalTimeTextField;
import services.DéfisS;

import java.io.File;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class DefisChef {
    @FXML
    private DatePicker date;

    @FXML
    private TextField description;

    @FXML
    private LocalTimeTextField heure;

    @FXML
    private TextField name;
    @FXML
    private Button ajout;


    @FXML
    private ImageView pho;
    public String imagePath = "";
    private AfficherDefis afficher;

    public void setAfficher(AfficherDefis afficher) {
        this.afficher = afficher;
    }

    @FXML
    public void ajouter(javafx.event.ActionEvent actionEvent) {
        if (name.getText().isEmpty() || description.getText().isEmpty() || date.getValue() == null || heure.getLocalTime() == null || imagePath.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs et sélectionner une image.");
            alert.show();
            return; // Exit method if any field is empty
        }
        // Check if the date format is yyyy-MM-dd
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.getValue().format(dateFormatter);
        if (!date.toString().equals(formattedDate)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez entrer l'heure au format  yyyy-MM-dd.");
            alert.show();
            return; // Exit method if date format is incorrect
        }

        // Check if the time format is HH:mm:ss
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = heure.getLocalTime().format(timeFormatter);
        if (!heure.toString().equals(formattedTime)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez entrer l'heure au format HH:mm:ss.");
            alert.show();
            return; // Exit method if time format is incorrect
        }
        User user = new User(6);
        Défis defis = new Défis(name.getText(),imagePath,description.getText(),date.getValue(),heure.getLocalTime(),user);
        DéfisS defisS = new DéfisS();

        try {
            defisS.ajouter(defis);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        Stage stage =(Stage)ajout.getScene().getWindow();
        stage.close();
       afficher.refreshView();

    }


    public void addphoto(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String imagePath = file.toURI().toString();
            System.out.println("Image Path: " + imagePath); // Debugging: Print out image path

            try {
                Image image = new Image(imagePath);
                pho.setImage(image);
                // Set the image to the ImageView
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error loading image: " + e.getMessage());
                alert.show();
            }

        }
        imagePath = file.getAbsolutePath();
    }
}
