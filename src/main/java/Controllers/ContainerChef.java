package Controllers;

import Entities.Recettes;
import Services.RecettesS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ContainerChef {


    @FXML
    private ImageView img_r_1;

    @FXML
    private Label label_desc_1;

    @FXML
    private Label label_nom_1;

    @FXML
    private Button showmore_1;

    Recettes recette ;
    AffichageChef pageChef ;
    public void set_Chef (Recettes recette) {

        label_nom_1.setText(recette.getNom());
        label_desc_1.setText(recette.getDescription());

        Image img = new Image ("/placeholder.jpg") ;
        img_r_1.setImage(img);


    }
    public void  setChef (Recettes r){
        this.recette = r ;
    }
    @FXML
    void deleteChef(ActionEvent event ) {

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Are you sure you want to delete this recipe?");


        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Call the service or repository method to delete the recipe
                RecettesS rs = new RecettesS();
                rs.supprimer(recette.getId_r());



                // Handle any exceptions that occur during deletion
            } catch (SQLException e) {
                e.printStackTrace();
                // Display an error message or handle the exception as needed
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Deletion Failed");
                errorAlert.setContentText("An error occurred while deleting the recipe. Please try again.");
                errorAlert.showAndWait();
            }
        }
    }

    @FXML
    void updateChef(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierAdmin.fxml"));
            Parent root = loader.load();

            ModifierAdmin updateController = loader.getController();
            updateController.initData(recette ,pageChef);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle update view loading error
        }

    }
    @FXML
    private void initialize() {
        // Set event handlers for the buttons

    }

}
