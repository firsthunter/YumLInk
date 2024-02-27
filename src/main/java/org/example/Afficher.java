package org.example;

import entities.Défis;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import services.DéfisS;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class Afficher {

    @FXML
    private GridPane GridPane;
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
            GridPane.getChildren().clear();

            int row = 1;
            int col = 0;

            // Iterate through the list of Défis
            for (Défis défis : défisList) {
                col++;
                // Load item.fxml for each Défis and set its data
                FXMLLoader Loader;
                Loader = new FXMLLoader(getClass().getResource("/chall.fxml"));
                Parent interfaceRoot = Loader.load();
                Chall itemController = Loader.getController();
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
            FXMLLoader root = new FXMLLoader(getClass().getResource("/defis.fxml"));
            Parent page = root.load();
            defiscontr defiscontr = root.getController();
            defiscontr.setAfficher(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(page));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
