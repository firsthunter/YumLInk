package org.example;

import entities.Défis;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import services.DéfisS;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DefisUser {
    @FXML
    private GridPane GridU;
    BaseController baseController;
    @FXML
    public void initialize() {
        loadContent();
    }

    private void loadContent() {
        try {// Fetch the list of Défis from the database
            DéfisS DS= new DéfisS();
            List<Défis> défisList = DS.afficher();
            // Clear the existing content of the GridPane
            GridU.getChildren().clear();

            int row = 1;
            int col = 0;

            // Iterate through the list of Défis
            for (Défis défis : défisList) {
                col++;
                // Load item.fxml for each Défis and set its data
                FXMLLoader Loader;
                Loader = new FXMLLoader(getClass().getResource("/DefisCardUser.fxml"));
                Parent interfaceRoot = Loader.load();
                DefisCardUser itemController = Loader.getController();
                itemController.setDataU(défis);
                itemController.setd(défis);
//                itemController.setRefresh(this);

                // Add the loaded item to the GridPane
                GridU.add(interfaceRoot, col, row);
                GridU.setHgap(20); // Set the horizontal gap between items
                GridU.setVgap(20); // Set the vertical gap between items

                // Adjust row and column indices
                if (col == 5) { // Change this value based on your layout
                    col = 0;
                    row++;
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }
}
