package org.example.yumlink_v3;

import Entities.Ingredient;
import Entities.Recettes;
import Services.IngredientS;
import Services.RecettesS;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utiles.DataBase;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        DataBase d = DataBase.getInstance();
        RecettesS ps = new RecettesS();
        ps.ajouter(new Recettes("titta","hchicha","hhh", 12 ,15));
        ps.supprimer(10);
        ps.modifier(new Recettes("hh","temimi","j",10,15));
        System.out.println(ps.afficher());
        IngredientS i = new IngredientS() ;
        i.ajouter(new Ingredient(1,"a",13));
        i.ajouter(new Ingredient(2,"b",1));
        i.ajouter(new Ingredient(3,"c",10));
        i.ajouter(new Ingredient(4,"d",14));
        System.out.println(i.afficher());
       //i.supprimer(1);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}