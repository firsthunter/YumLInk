package org.example;

import entities.Participant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import services.ParticipantS;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherPartic {

    @FXML
    private GridPane Grid;
    BaseController baseController;
    @FXML
    public void initialize() {
        loadContent();
    }

    private void loadContent() {
        try {
            ParticipantS PS = new ParticipantS();
            List<Participant> ParticiList = PS.afficher();

            Grid.getChildren().clear();

            int row = 1;
            int col = 0;


            for (Participant participant : ParticiList) {
                col++;

                FXMLLoader Loader;
                Loader = new FXMLLoader(getClass().getResource("/challP.fxml"));
                Parent interfaceRoot = Loader.load();
                ChallP itemController = Loader.getController();
                itemController.setData(participant);
                itemController.setD(participant);
                itemController.setRefresh(this);

                // Add the loaded item to the GridPane
                Grid.add(interfaceRoot, col, row);
                Grid.setHgap(20); // Set the horizontal gap between items
                Grid.setVgap(20); // Set the vertical gap between items

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
}
