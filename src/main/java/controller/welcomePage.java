package controller;

import entities.Adresse;
import entities.ApplicationContext;
import entities.User;
import entities.sessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.AdresseService;
import service.UserService;

import java.io.IOException;
import java.sql.SQLException;

public class welcomePage {
    UserService us=new UserService();
    AdresseService as=new AdresseService();
    @FXML
    private Button btnBlog;

    @FXML
    private Button btnBlog1;

    @FXML
    private Button btnChef;

    @FXML
    private Button btnDefis;

    @FXML
    private Button btnMyAccount;

    @FXML
    private Button btnRecette;

    @FXML
    private Button btnShop;

    @FXML
    private ImageView logoutImage;

    @FXML
    private Pane welcomePane;

    @FXML
    void btnMyAccount(ActionEvent event) throws IOException, SQLException {
        if (event.getSource()==btnMyAccount){
            System.out.println("nzelt");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/yumlink/firstPage.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            sessionManager userSession = ApplicationContext.getInstance().getUsersession();
            int id=userSession.getIdU();
            int idA= userSession.getIdA();
            User user=us.getUserDetailsById(id);
            Adresse adresse=as.getAdresseDetailsById(idA);
            firstPage controller = loader.getController();
            controller.setUserDetails(user);
            controller.setAdresseDetails(adresse);
        }

    }



    @FXML
    void logOutClicked(MouseEvent event) {

    }
}
