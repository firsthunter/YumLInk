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
            // Get input values
            String ageText = tf_age.getText().trim();
            String weightText = tf_poids.getText().trim();
            String heightText = tf_taille.getText().trim();
            String userActivityLevel = actif.isSelected() ? "Active" : lazy.isSelected() ? "Lazy": "";
            String userGender = Femme.isSelected() ? "Femme" : Homme.isSelected() ? "Homme" : "";

            // Check for empty fields
            if (ageText.isEmpty() || weightText.isEmpty() || heightText.isEmpty() || userActivityLevel.isEmpty() || userGender.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Veuillez remplir tous les champs");
                alert.show();
                return; // Exit the method if any field is empty
            }

            // Parse input values
            try {
                int userAge = Integer.parseInt(ageText);
                double userWeight = Double.parseDouble(weightText);
                double userHeight = Double.parseDouble(heightText);

                // Validate age, height, and weight
                if (userAge <= 15 || userHeight <= 140 || userHeight >= 210 || userWeight <= 40 || userWeight >= 250) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Veuillez saisir des valeurs valides pour l'âge entre 15 et 100, le poids entre 40 et 250 et la taille entre 140 et 210");
                    alert.show();
                    return; // Exit the method if validation fails
                }

                // Add user nutrition and get nutrition values
                Map<String, Double> nutritionValues = un.addUserNutrition(new UserNutrition(userAge, userWeight, userHeight, userActivityLevel, userGender));

                // Get the nutrition values from the map
                double calorie = nutritionValues.get("calorie");
                double protein = nutritionValues.get("protein");
                double carbs = nutritionValues.get("carbs");
                double fats = nutritionValues.get("fats");

                // Display the nutrition values in labels
                label_calorie.setText(String.format("%.1f", calorie));
                label_carbs.setText(String.format("%.1f", carbs));
                label_fats.setText(String.format("%.1f", fats));
                label_protein.setText(String.format("%.1f", protein));


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Info ajoutée avec succès");
                alert.show();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Veuillez saisir des valeurs numériques valides pour l'âge, le poids et la taille");
                alert.show();
            }
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
       int userId =1;

            // Retrieve changed values from the text fields and radio buttons
            int newAge = Integer.parseInt(tf_age.getText());
            double newWeight = Double.parseDouble(tf_poids.getText());
            System.out.println(newAge);
            double newHeight = Double.parseDouble(tf_taille.getText());
            String newActivityLevel = actif.isSelected() ? "Active" : "Lazy";
            String newGender = Femme.isSelected() ? "Femme" : "Homme";

            // Create a new UserNutrition instance (un1) with both sets of values
            UserNutrition un1 = new UserNutrition(newAge, newWeight, newHeight, newActivityLevel, newGender);


            // Update the user nutrition data in the database with the new instance (un1)
            System.out.println(un1);
            new UserNutrition_Service().updateUserNutrition(un1);


            // Optionally, display a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("User nutrition data updated successfully.");
            alert.show();
        }
    @FXML
    void Affiche_donnee_user(ActionEvent event) {
        int userId = 1; // Replace this with the actual user ID
        UserNutrition un = UserNutrition_Service.getUserNutrition(userId);
        System.out.println(un);
        if (un != null) {
            // Display the user nutrition data in labels

            label_calorie.setText("Submit ");
            label_protein.setText("Submit ");
            label_fats.setText("Submit ");
            label_carbs.setText("Submit ");
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





