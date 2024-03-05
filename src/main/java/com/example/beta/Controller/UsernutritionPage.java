package com.example.beta.Controller;

import com.example.beta.Models.PDFGenerator;
import com.example.beta.Models.Recettes;
import com.example.beta.Models.UserNutrition;
import com.example.beta.Services.UserNutrition_Service;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
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
    private TextField plat_choisie;

    @FXML
    private TableView<Recettes> recettesTable;

    @FXML
    private TableColumn<Recettes, String> nomCol;

    @FXML
    private TableColumn<Recettes, Integer> calorieCol;

    @FXML
    private TableColumn<Recettes, Integer> proteinCol;


    private RecommendationController recommendationController;

    public void setRecommendationController(RecommendationController recommendationController) {
        this.recommendationController = recommendationController;
    }

    BaseController baseController;

    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    @FXML
    void ajouter_user_info(ActionEvent event) {

        if (event.getSource() == ajouter_info) {
            UserNutrition_Service un = new UserNutrition_Service();
            // Get input values
            String ageText = tf_age.getText().trim();
            String weightText = tf_poids.getText().trim();
            String heightText = tf_taille.getText().trim();
            String userActivityLevel = actif.isSelected() ? "Active" : lazy.isSelected() ? "Lazy" : "";
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

                RecommendationController rc = new RecommendationController();

                // Call the method to get meal recommendation
                List<Recettes> recettesList = rc.getmealRecommendation((int) calorie);

                // Populate the recettes table
                populateRecettesTable(recettesList);


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
        int userId = 1;
        try {
            // Retrieve changed values from the text fields and radio buttons
            int newAge = Integer.parseInt(tf_age.getText());
            double newWeight = Double.parseDouble(tf_poids.getText());
            double newHeight = Double.parseDouble(tf_taille.getText());
            String newActivityLevel = actif.isSelected() ? "Active" : "Lazy";
            String newGender = Femme.isSelected() ? "Femme" : "Homme";
            if (newAge < 15 || newAge > 100) {
                throw new IllegalArgumentException("L'âge doit être compris entre 15 et 100");

            }

            if (newWeight < 40 || newWeight > 250) {
                throw new IllegalArgumentException("Le poids doit être compris entre 40 et 250");
            }

            if (newHeight < 140 || newHeight > 210) {
                throw new IllegalArgumentException("La taille doit être comprise entre 140 et 210");
            }


            // Create a new UserNutrition instance (un1) with both sets of values
            UserNutrition un1 = new UserNutrition(newAge, newWeight, newHeight, newActivityLevel, newGender);


            // Update the user nutrition data in the database with the new instance (un1)
            System.out.println(un1);
            new UserNutrition_Service().updateUserNutrition(un1);


            // Optionally, display a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("User nutrition data updated successfully.");
            alert.show();
            //refresh table
            recettesTable.refresh();
        } catch (NumberFormatException e) {
            // Handle invalid number format
            System.err.println("Erreur de format de nombre : " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez saisir des valeurs numériques valides.");
            alert.show();
        } catch (IllegalArgumentException e) {
            // Handle invalid input range
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        ;
    }

    @FXML
    void Affiche_donnee_user(ActionEvent event) {
        int userId = 1; // Replace this with the actual user ID
        UserNutrition_Service userNutritionService = new UserNutrition_Service();
        UserNutrition un = new UserNutrition();
        un = userNutritionService.getUserNutrition(userId);
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

    @FXML
    void download_pdf(ActionEvent event) {
        int age = Integer.parseInt(tf_age.getText().trim());
        double weight = Double.parseDouble(tf_poids.getText().trim());
        double height = Double.parseDouble(tf_taille.getText().trim());
        String gender = Femme.isSelected() ? "Femme" : Homme.isSelected() ? "Homme" : "";
        String activityLevel = actif.isSelected() ? "Active" : lazy.isSelected() ? "Lazy" : "";
        String calorie = label_calorie.getText();
        String fats = label_fats.getText();
        String carbs = label_carbs.getText();

        Map<String, Double> vitamins = new HashMap<>();
        vitamins.put("Vitamin A", 100.0);
        vitamins.put("Vitamin B1", 50.0);
        vitamins.put("Vitamin B2", 60.0);
        vitamins.put("Vitamin B3", 70.0);
        vitamins.put("Vitamin B5", 80.0);
        vitamins.put("Vitamin B6", 90.0);
        vitamins.put("Vitamin B7", 100.0);
        vitamins.put("Vitamin B9", 110.0);
        vitamins.put("Vitamin B12", 120.0);
        vitamins.put("Vitamin C", 130.0);
        vitamins.put("Vitamin D", 140.0);
        vitamins.put("Vitamin E", 150.0);
        vitamins.put("Vitamin K", 160.0);
        vitamins.put("Calcium", 170.0);
        vitamins.put("Iron", 180.0);
        vitamins.put("Magnesium", 190.0);
        vitamins.put("Phosphorus", 200.0);
        vitamins.put("Potassium", 210.0);
        vitamins.put("Sodium", 220.0);
        vitamins.put("Zinc", 230.0);
        vitamins.put("Copper", 240.0);
        vitamins.put("Manganese", 250.0);
        vitamins.put("Selenium", 260.0);
        vitamins.put("Choline", 270.0);
        vitamins.put("Biotin", 280.0);
        vitamins.put("Pantothenic Acid", 290.0);
        vitamins.put("Riboflavin", 300.0);
        vitamins.put("Thiamine", 310.0);
        vitamins.put("Niacin", 320.0);
        try {
            PDFGenerator pdfGenerator = new PDFGenerator();
            pdfGenerator.createPDF(age, (int) weight, (int) height, gender, activityLevel, calorie, fats, carbs, vitamins);
            System.out.println("PDF generated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void populateRecettesTable(List<Recettes> x) {
        ObservableList<Recettes> observableRecettesList = FXCollections.observableArrayList();

        // Add all Recettes objects from the recettesList to the ObservableList
        observableRecettesList.addAll(x);

        // Set the items of the recettesTable to the ObservableList
        recettesTable.setItems(observableRecettesList);

        // Set cell value factories for each column
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        calorieCol.setCellValueFactory(new PropertyValueFactory<>("calorie"));
        proteinCol.setCellValueFactory(new PropertyValueFactory<>("protein"));


    }

    @FXML
    void get_regime(ActionEvent event) {
        String pc = plat_choisie.getText();
        if (!pc.isEmpty()) {
            try {
                URL url = new URL("https://api.edamam.com/api/nutrition-details?app_id=58aea65e&app_key=8857ecdc438404779d04b57881f1a456");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Construct the request body
                String requestBody = "{\"title\": \"Recipe Title\",\"ingr\": [\"" + pc + "\"]}";

                // Send the request body
                try (OutputStream os = connection.getOutputStream();
                     OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8")) {
                    osw.write(requestBody);
                    osw.flush();
                }

                // Get the response
                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                }

                Gson gson = new Gson();
                JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);

                // Extract the nutrient values
                JsonObject totalNutrients = jsonResponse.getAsJsonObject("totalNutrients");
                double calories = totalNutrients.getAsJsonObject("ENERC_KCAL").get("quantity").getAsDouble();
                double protein = totalNutrients.getAsJsonObject("PROCNT").get("quantity").getAsDouble();
                double fats = totalNutrients.getAsJsonObject("FAT").get("quantity").getAsDouble();
                double carbs = totalNutrients.getAsJsonObject("CHOCDF").get("quantity").getAsDouble();

                // Construct the alert message with the extracted nutrient values
                String alertMessage = "Calories: " + calories + " kcal\n" +
                        "Protein: " + protein + " g\n" +
                        "Fats: " + fats + " g\n" +
                        "Carbs: " + carbs + " g";

                // Show the alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Nutrient Analysis");
                alert.setHeaderText("Nutrient Values");
                alert.setContentText(alertMessage);
                alert.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
                // Display error message to user
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Unable to Analyze Recipe");
                errorAlert.setContentText("Error: Unable to analyze recipe. Please try again later.");
                errorAlert.showAndWait();
            }
        } else {
            // Prompt user to enter recipe
            Alert inputAlert = new Alert(Alert.AlertType.WARNING);
            inputAlert.setTitle("Warning");
            inputAlert.setHeaderText("Empty Recipe");
            inputAlert.setContentText("Please enter a recipe to analyze.");
            inputAlert.showAndWait();
        }
    }

    @FXML
    void on_press_speak(MouseEvent event) {
        String weightStr = tf_poids.getText(); // Assuming 'tf_poids' is the TextField containing the weight as a string

        try {
            double weight = Double.parseDouble(weightStr);

            // Set up the voice
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            VoiceManager voiceManager = VoiceManager.getInstance();
            Voice voice = voiceManager.getVoice("kevin16");
            if (voice == null) {
                System.err.println("Cannot find a voice named kevin16. Please check your configuration.");
                return;
            }
            voice.allocate();

            // Speak the appropriate message based on weight
            if (weight > 85) {
                voice.speak("You are overweight.");
            } else if (weight < 55) {
                voice.speak("You need to eat more.");
            } else {
                voice.speak("You are normal.");
            }

            // Deallocate the voice resources
            voice.deallocate();

        } catch (NumberFormatException e) {
            // Handle the case where the text in the TextField is not a valid number
            System.err.println("Invalid weight input: " + weightStr);
        }


    }
}





