package com.example.beta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label clickmelabel;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX  Application!");
    }
    protected void ClickButtonClick() {
        clickmelabel.setText("heeeeeeellooooo!");
    }

    @FXML
    protected void ajouter_donnee_user(ActionEvent event) {

       ;
    }
}