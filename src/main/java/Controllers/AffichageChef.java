package Controllers;

import Entities.Ingredient;
import Entities.Recettes;
import Services.RecettesS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AffichageChef {
    @FXML
    private GridPane gridChef;
    @FXML
    private Button ajouterRChef;


    public void initialize() {
        loadChef();

    }

private void loadChef() {
        try {// Fetch the list of Défis from the database
            RecettesS RS = new RecettesS();
            List<Recettes> resL = RS.afficher();
            // Clear the existing content of the GridPane
            gridChef.getChildren().clear();

            int row = 1;
            int col = 0;

            // Iterate through the list of Défis
            for (Recettes recettes : resL) {
                col++;
                // Load item.fxml for each Défis and set its data
                FXMLLoader Loader;
                Loader = new FXMLLoader(getClass().getResource("/ContainerChef.fxml"));
                Parent interfaceRoot = Loader.load();
                ContainerChef itemController = Loader.getController();
                itemController.set_Chef(recettes);
                itemController.setChef(recettes);
               // itemController.deleteChef(recettes); //buttons

                // Add the loaded item to the GridPane
                gridChef.add(interfaceRoot, col, row);
                gridChef.setHgap(20); // Set the horizontal gap between items
                gridChef.setVgap(20); // Set the vertical gap between items

                // Adjust row and column indices
                if (col == 4) { // Change this value based on your layout
                    col = 0;
                    row++;
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }


    }
public void refrech (){
    loadChef() ;
}
    @FXML
    void addRchef(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddRecetteChef.fxml"));
            Parent root = loader.load();

            // Set the FXML file as the root of the scene
            Scene scene = new Scene(root);

            // Get the current stage from the button's scene
            Stage stage = (Stage) ajouterRChef.getScene().getWindow();

            // Set the scene to the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}