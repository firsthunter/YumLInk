package org.example;

import entities.Défis;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.DéfisS;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Update {

    @FXML
    private TextField DateU;

    @FXML
    private TextField DisU;

    @FXML
    private TextField HeureU;



    @FXML
    private ImageView ImageU;

    @FXML
    private TextField NomU;
    @FXML
    private Button UpdateB;
    private Défis défis;
    private Afficher afficher;
    private String imageP ="";
    public void initData(Défis défis, Afficher afficher) {
        this.défis = défis;
        this.afficher = afficher;
        populateFields();
    }

    private void populateFields() {
        NomU.setText(défis.getNom_d());
        DisU.setText(défis.getDis_d());

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateU.setText(défis.getDelai().format(dateFormatter));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        HeureU.setText(défis.getHeure().format(timeFormatter));
        File file = new File(défis.getPhoto_d());
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            ImageU.setImage(image);
        }
    }

    @FXML
    void AddP(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                // Load image
                Image image = new Image(selectedFile.toURI().toURL().toString());
                // Set image
                ImageU.setImage(image);
                imageP = selectedFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
                // Handle image loading error
            }
        }

    }

    @FXML
    void Update(ActionEvent event) {
        if (défis != null) {
            try {
                if(!imageP.isEmpty()) {
                    défis.setNom_d(NomU.getText());
                    défis.setDis_d(DisU.getText());
                    défis.setDelai(LocalDate.parse(DateU.getText()));
                    défis.setHeure(LocalTime.parse(HeureU.getText()));
                    défis.setPhoto_d(imageP);

                    DéfisS DS = new DéfisS();
                    DS.modifier(défis);
                }else {
                    défis.setNom_d(NomU.getText());
                    défis.setDis_d(DisU.getText());
                    défis.setDelai(LocalDate.parse(DateU.getText()));
                    défis.setHeure(LocalTime.parse(HeureU.getText()));
                    défis.setPhoto_d(défis.getPhoto_d());

                    DéfisS DS = new DéfisS();
                    DS.modifier(défis);
                }
                Stage stage=(Stage)UpdateB.getScene().getWindow();
                stage.close();
                // Refresh the view in the Afficher controller
                afficher.refreshView();


            } catch (SQLException e) {
                e.printStackTrace();
                // Handle update error
            }
        }
    }
      /* private void loadPage(String fxmlFileName , ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the case where the FXML file cannot be loaded
        }*/

    }


