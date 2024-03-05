package com.example.beta.Controller;

import com.example.beta.Models.Recettes;
import com.example.beta.Services.mealRecommendation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.util.List;


public class RecommendationController {
    private mealRecommendation mealRecommendation ;
    private TableView<Recettes> recettesTable; // TableView reference
    public void init (){
        recettesTable = new TableView<>();
    }
    public RecommendationController() {
        this.mealRecommendation = new mealRecommendation();

    }

    public List<Recettes> getmealRecommendation(int calorieThreshold) {
        return mealRecommendation.getRecettesUnderCalorieThreshold(calorieThreshold);
    }


}
