package com.example.beta.Services;

import com.example.beta.Controller.RecommendationController;
import com.example.beta.Models.Recettes;
import com.example.beta.Utils.My_db;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.*;
import java.sql.SQLException;


import java.util.List;

public class mealRecommendation {

    private static Connection conn;

    public mealRecommendation() {
        conn = My_db.getInstance().getConn();
    }


    // Method to get recipes with calorie counts under a certain threshold
    public List<Recettes> getRecettesUnderCalorieThreshold(int calorieThreshold) {
        List<Recettes> recettesList = new ArrayList<>();


            String sql = "SELECT * FROM recette WHERE calorie < ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, calorieThreshold /3);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String nom = resultSet.getString("nom");
                        int calorie = resultSet.getInt("calorie");
                        int protein = resultSet.getInt("protein");

                        Recettes recette = new Recettes( nom, calorie, protein);
                        recettesList.add(recette);
                    }
                }
            }
         catch (SQLException e) {
            e.printStackTrace();
        }

        return recettesList;
    }
    public ObservableList<Recettes> getObservableRecettesList(int calorieThreshold) {
        List<Recettes> recettesList = getRecettesUnderCalorieThreshold(calorieThreshold);
        return FXCollections.observableArrayList(recettesList);
    }

}

