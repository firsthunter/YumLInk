package tn.yumlink.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/UI/Base.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("YumLink");
        stage.getIcons().add(new Image("/img/logo.png"));
        stage.setScene(scene);
        stage.show();
        stage.widthProperty().addListener((o, oldValue, newValue) -> {
            if (newValue.intValue() < 1180.0) {
                stage.setResizable(false);
                stage.setWidth(1180.0);
                stage.setResizable(true);
            }
        });
    }
    public static void main(String[] args) {
        launch();
    }
}