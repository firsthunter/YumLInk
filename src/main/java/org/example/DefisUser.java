package org.example;

import entities.Défis;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import services.DéfisS;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DefisUser {
    @FXML
    private GridPane GridU;
    BaseController baseController;
    @FXML
    private TextField searchFieldU;

    @FXML
    public void initialize() {
        searchFieldU.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                loadContent(); // Reload content when search field is cleared
            } else {
                searchDefisU(newValue); // Filter Défis based on the search keyword
            }
        });
        loadContent();
    }

    private void searchDefisU(String newValue) {
        try {
            // Fetch the list of Défis from the database
            DéfisS DS = new DéfisS();
            List<Défis> défisList = DS.afficher();

            // Clear the existing content of the GridPane
            GridU.getChildren().clear();

            int row = 1;
            int col = 1;

            // Iterate through the list of Défis
            for (Défis défis : défisList) {
                if (défis.getNom_d().toLowerCase().contains(newValue.toLowerCase())) {
                    // Load item.fxml for matching Défis and set its data
                    FXMLLoader Loader = new FXMLLoader(getClass().getResource("/DefisCardUser.fxml"));
                    Parent interfaceRoot = Loader.load();
                    DefisCardUser itemController = Loader.getController();
                    itemController.setDataU(défis);
                    itemController.setd(défis);
                   // itemController.setRefreshh(this);

                    // Add the loaded item to the GridPane
                    GridU.add(interfaceRoot, col, row);
                    GridU.setHgap(20); // Set the horizontal gap between items
                    GridU.setVgap(20); // Set the vertical gap between items

                    // Adjust row and column indices
                    if (++col == 5) { // Change this value based on your layout
                        col = 0;
                        row++;
                    }
                }
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    private void loadContent() {
        try {// Fetch the list of Défis from the database
            DéfisS DS= new DéfisS();
            List<Défis> défisList = DS.afficher();
            Collections.sort(défisList, Comparator.comparing(Défis::getDelai));
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
              // itemController.setRefresh(this);

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
