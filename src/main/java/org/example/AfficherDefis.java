package org.example;

import entities.Défis;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import services.DéfisS;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AfficherDefis {

    @FXML
    private GridPane GridPane;
    BaseController baseController;

    @FXML
    private TextField searchField;

@FXML
    public void initialize() {
    searchField.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue.isEmpty()) {
            loadContent(); // Reload content when search field is cleared
        } else {
            searchDefis(newValue); // Filter Défis based on the search keyword
        }
    });
    loadContent();
    }

    private void searchDefis(String keyword) {
        try {
            // Fetch the list of Défis from the database
            DéfisS DS = new DéfisS();
            List<Défis> défisList = DS.afficher();

            // Clear the existing content of the GridPane
            GridPane.getChildren().clear();

            int row = 1;
            int col = 1;

            // Iterate through the list of Défis
            for (Défis défis : défisList) {
                if (défis.getNom_d().toLowerCase().contains(keyword.toLowerCase())) {
                    // Load item.fxml for matching Défis and set its data
                    FXMLLoader Loader = new FXMLLoader(getClass().getResource("/DefisCardChef.fxml"));
                    Parent interfaceRoot = Loader.load();
                    DefisCardChef itemController = Loader.getController();
                    itemController.setData(défis);
                    itemController.setD(défis);
                    itemController.setRefresh(this);

                    // Add the loaded item to the GridPane
                    GridPane.add(interfaceRoot, col, row);
                    GridPane.setHgap(20); // Set the horizontal gap between items
                    GridPane.setVgap(20); // Set the vertical gap between items

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
        try {
            DéfisS DS = new DéfisS();
            LocalDateTime currentDateTime = LocalDateTime.now();

            // Fetch only non-expired Défis
            List<Défis> défisList = DS.getNonExpiredDefis(currentDateTime);

            // Sort the list by date using Comparator
            Collections.sort(défisList, Comparator.comparing(Défis::getDelai));

            // Clear the existing content of the GridPane
            GridPane.getChildren().clear();

            int row = 1;
            int col = 0;

            // Iterate through the list of Défis
            for (Défis défis : défisList) {
                col++;
                // Load item.fxml for each Défis and set its data
                FXMLLoader Loader = new FXMLLoader(getClass().getResource("/DefisCardChef.fxml"));
                Parent interfaceRoot = Loader.load();
                DefisCardChef itemController = Loader.getController();
                itemController.setData(défis);
                itemController.setD(défis);
                itemController.setRefresh(this);

                // Add the loaded item to the GridPane
                GridPane.add(interfaceRoot, col, row);
                GridPane.setHgap(20); // Set the horizontal gap between items
                GridPane.setVgap(20); // Set the vertical gap between items

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

    public void refreshView() {
        loadContent();
    }

    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }


    public void AjouterDefis(ActionEvent actionEvent) {
        try {
            FXMLLoader root = new FXMLLoader(getClass().getResource("/DefisChef.fxml"));
            Parent page = root.load();
            DefisChef defiscontr = root.getController();
            defiscontr.setAfficher(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(page));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
