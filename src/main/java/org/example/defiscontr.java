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

public class defiscontr {
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
    private Afficher afficher;

    public void setAfficher(Afficher afficher) {
        this.afficher = afficher;
    }

    @FXML
    public void ajouter(javafx.event.ActionEvent actionEvent) {
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
