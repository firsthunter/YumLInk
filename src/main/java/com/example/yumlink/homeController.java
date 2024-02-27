package com.example.yumlink;

import java.io.File;
import java.io.ObjectInputFilter;
import java.util.ResourceBundle;

import java.net.URL;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author SFernando
 */
public class homeController {

    private static final String DECORATION_BUTTON_RESTORE = "decoration-button-restore";



    private Stage stage;

    private Rectangle2D rec2;

    private Double width;

    private Double height;

    @FXML
    private Button maximize;

    @FXML
    private Button minimize;

    @FXML
    private Button resize;

    @FXML
    private Button fullscreen;

    @FXML
    private ListView<String> listMenu;

    @FXML
    private AnchorPane paneData;

    @FXML
    private Button btnLogout;
    @FXML
    private Button homeb;
    @FXML
    private Button homeb1;
    @FXML
    private Button homeb11;


    public void initialize()
{

    Image iconImage = new Image(new File("C:/Users/horch/Desktop/YumLink/src/main/java/com/example/yumlink/accueil.png").toURI().toString());
    Image iconImage1= new Image(new File("C:/Users/horch/Desktop/YumLink/src/main/java/com/example/yumlink/maison.png").toURI().toString());
    Image iconImage2= new Image(new File("C:/Users/horch/Desktop/YumLink/src/main/java/com/example/yumlink/market.png").toURI().toString());


// Create an ImageView with the icon/image
    ImageView iconImageView = new ImageView(iconImage);
    ImageView iconImageView1 = new ImageView(iconImage1);
    ImageView iconImageView2 = new ImageView(iconImage2);
    // Set the fit width and fit height to resize the icon
    double iconWidth = 40; // Set the desired width
    double iconHeight = 40; // Set the desired height
    iconImageView.setFitWidth(iconWidth);
    iconImageView.setFitHeight(iconHeight);
    iconImageView1.setFitWidth(iconWidth);
    iconImageView1.setFitHeight(iconHeight);
    iconImageView2.setFitWidth(iconWidth);
    iconImageView2.setFitHeight(iconHeight);

// Set the graphic content of the button to the ImageView
   // homeb.setGraphic(iconImageView);
    homeb.setGraphic(iconImageView);
    homeb1.setGraphic(iconImageView1);
    homeb11.setGraphic(iconImageView2);

// Set the action for the button
    homeb.setOnAction(e -> System.out.println("Button clicked!"));


}
    @FXML
    private void maximize()
    {


        stage = (Stage) maximize.getScene().getWindow();
        if (stage.isMaximized())
        {
            if (width == rec2.getWidth() && height == rec2.getHeight())
            {
                stage.setMaximized(false);
                stage.setHeight(600);
                stage.setWidth(800);
                stage.centerOnScreen();
                maximize.getStyleClass().remove(DECORATION_BUTTON_RESTORE);
                resize.setVisible(true);
            }
            else
            {
                stage.setMaximized(false);
                maximize.getStyleClass().remove(DECORATION_BUTTON_RESTORE);
                resize.setVisible(true);
            }

        }
        else
        {
            stage.setMaximized(true);
            stage.setHeight(rec2.getHeight());
            maximize.getStyleClass().add(DECORATION_BUTTON_RESTORE);
            resize.setVisible(false);
        }
    }

    @FXML
    private void minimize()
    {
        stage = (Stage) minimize.getScene().getWindow();
        if (stage.isMaximized())
        {
            width = rec2.getWidth();
            height = rec2.getHeight();
            stage.setMaximized(false);
            stage.setHeight(height);
            stage.setWidth(width);
            stage.centerOnScreen();
            Platform.runLater(() -> {
                stage.setIconified(true);
            });
        }
        else
        {
            stage.setIconified(true);
        }
    }

    @FXML
    private void resize()
    {
        //TODO
    }

    @FXML
    private void fullscreen()
    {
        stage = (Stage) fullscreen.getScene().getWindow();
        if (stage.isFullScreen())
        {
            stage.setFullScreen(false);
        }
        else
        {
            stage.setFullScreen(true);
        }
    }

    @FXML
    private void close()
    {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void clickListMenu()
    {

    }

    @FXML
    private void logout()
    {
       // ObjectInputFilter.Config config = new ObjectInputFilter.Config();
       // config.newStage(btnLogout, "/sand/view/Login.fxml", "Sample Apps", true, StageStyle.UNDECORATED, false);
    }

}
