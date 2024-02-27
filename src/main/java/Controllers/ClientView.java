package Controllers;

import Entities.Recettes;
import Services.RecettesS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ClientView {
    @FXML
    private GridPane grid;

    public void initialize (){
        loadRecette();
    }

    private void loadRecette() {
        try {// Fetch the list of Défis from the database
           RecettesS RS= new RecettesS();
            List<Recettes> resL = RS.afficher();
            // Clear the existing content of the GridPane
            grid.getChildren().clear();

            int row = 1;
            int col = 0;

            // Iterate through the list of Défis
            for (Recettes recettes : resL) {
                col++;
                // Load item.fxml for each Défis and set its data
                FXMLLoader Loader;
                Loader = new FXMLLoader(getClass().getResource("/RecepieContainer.fxml"));
                Parent interfaceRoot = Loader.load();
                RecepieContainer itemController = Loader.getController();
                itemController.set_Recttes(recettes);
               // itemController.set_Recttes(recettes);
               // itemController.setRefresh(this); //buttons

                // Add the loaded item to the GridPane
                grid.add(interfaceRoot, col, row);
                grid.setHgap(20); // Set the horizontal gap between items
                grid.setVgap(20); // Set the vertical gap between items

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

}}