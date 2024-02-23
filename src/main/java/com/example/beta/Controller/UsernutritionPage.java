package com.example.beta.Controller;

import com.example.beta.Models.UserNutrition;
import com.example.beta.Services.UserNutrition_Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.Map;

public class UsernutritionPage {

    @FXML
    private RadioButton Femme;

    @FXML
    private RadioButton Homme;

    @FXML
    private RadioButton actif;

    @FXML
    private TextField tf_age;

    @FXML
    private Button ajouter_info;

    @FXML
    private RadioButton lazy;

    @FXML
    private ListView<?> listview;

    @FXML
    private TextField tf_poids;

    @FXML
    private RadioButton super_actif;

    @FXML
    private TextField tf_taille;
    @FXML
    private Label label_calorie;

    @FXML
    private Label label_carbs;

    @FXML
    private Label label_fats;

    @FXML
    private Label label_protein;




    @FXML
    void ajouter_user_info(ActionEvent event) {

        if (event.getSource() == ajouter_info) {
            UserNutrition_Service un = new UserNutrition_Service();
            int userAge = Integer.parseInt(tf_age.getText());
            double userWeight = Double.parseDouble(tf_poids.getText());
            double userHeight = Double.parseDouble(tf_taille.getText());
            String userActivityLevel = actif.isSelected() ? "Active" : lazy.isSelected() ? "Lazy" : super_actif.isSelected() ? "Super Active" : "";
            String userGender = Femme.isSelected() ? "Femme" : Homme.isSelected() ? "Homme" : "";

            if (userActivityLevel.isEmpty() || userGender.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Veuillez remplir tous les champs");
                alert.show();
            }
            Map<String, Double> nutritionValues = un.addUserNutrition(new UserNutrition(userAge, userWeight, userHeight, userActivityLevel,userGender));
// Get the nutrition values from the map ou min function add
            double calorie = nutritionValues.get("calorie");
            double protein = nutritionValues.get("protein");
            double carbs = nutritionValues.get("carbs");
            double fats = nutritionValues.get("fats");

// Display the nutrition values in label
            label_calorie.setText(String.valueOf(calorie));
            label_carbs.setText(String.valueOf(carbs));
            label_fats.setText(String.valueOf(fats));
            label_protein.setText(String.valueOf(protein));


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("info ajouter avec success");
            alert.show();

        }

    }
    @FXML
    void supprimer_donnee_user(ActionEvent event) {
        int userid = 1;
        UserNutrition_Service un = new UserNutrition_Service();
        un.deleteUserNutrition(userid);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Donees supprimer avec success");
        alert.show();
    }

    @FXML
   void update_donnee_user(ActionEvent event) {
      /*   int userId =1;
        try {
            UserNutrition un = new UserNutrition();
            UserNutrition userNutrition = un.(userId);
            if (un != null) {
                tf_age.setText(String.valueOf(un.getAge()));
                tf_poids.setText(String.valueOf(un.getWeight()));
                tf_taille.setText(String.valueOf(un.getHeight()));
                actif.setText(un.getActivityLevel());
                Homme.setText(un.getGender());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    */}
    @FXML
    void Affiche_donnee_user(ActionEvent event) {
        int userId = 1; // Replace this with the actual user ID
        UserNutrition un = UserNutrition_Service.getUserNutrition(userId);
        if (un != null) {
            // Display the user nutrition data in labels
            label_calorie.setText(String.valueOf(un.getCalories()));
            label_protein.setText(String.valueOf(un.getProtein()));
            label_fats.setText(String.valueOf(un.getFat()));
            label_carbs.setText(String.valueOf(un.getCarbs()));
            tf_age.setText(String.valueOf(un.getAge()));
            tf_poids.setText(String.valueOf(un.getWeight()));
            tf_taille.setText(String.valueOf(un.getHeight()));
            if (un.getGender().equalsIgnoreCase("Femme")) {
                Femme.setSelected(true);
            } else if (un.getGender().equalsIgnoreCase("Homme")) {
                Homme.setSelected(true);
            }
            switch (un.getActivityLevel()) {
                case "Active":
                    actif.setSelected(true);
                    break;
                case "Lazy":
                    lazy.setSelected(true);
                    break;
            }
         } else {
            // Handle case when no user nutrition data is found
            System.out.println("User nutrition data not found for ID: " + userId);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Pas de donnee trouver");
            alert.show();
        }
    }

}





