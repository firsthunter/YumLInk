package controller;

import entities.Adresse;
import entities.ApplicationContext;
import entities.User;
import entities.sessionManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.AdresseService;
import service.UserService;

import java.io.IOException;
import java.sql.SQLException;

public class firstPage extends Application {
    UserService userService = new UserService();
    AdresseService adresseService=new AdresseService();
    @FXML
    private ImageView UserImage;

    @FXML
    private ImageView btnBack;

    @FXML
    private Button btnBlog;

    @FXML
    private Button btnBlog1;

    @FXML
    private Button btnChef;

    @FXML
    private Button btnDefis;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnMyAccount;

    @FXML
    private Button btnRecette;

    @FXML
    private Button btnShop;

    @FXML
    private Button btnSwitchToModif;

    @FXML
    private ImageView logoutImage;

    @FXML
    private Pane paneDetails;

    @FXML
    private Pane paneModify;

    @FXML
    private TextField tfCodePostalD;

    @FXML
    private TextField tfEmailD;

    @FXML
    private TextField tfEmailM;

    @FXML
    private TextField tfGouvD;

    @FXML
    private TextField tfGouverM;

    @FXML
    private TextField tfMdpD;

    @FXML
    private TextField tfMdpM;

    @FXML
    private TextField tfNomD;

    @FXML
    private TextField tfNomM;

    @FXML
    private TextField tfPrenomD;

    @FXML
    private TextField tfPrenomM;

    @FXML
    private TextField tfRueD;

    @FXML
    private TextField tfRueM;

    @FXML
    private TextField tfTelD;

    @FXML
    private TextField tfTelM;

    @FXML
    private TextField tfVilleD;

    @FXML
    private TextField tfVilleM;

    @FXML
    private TextField tfcodePM;


    @FXML
    public void initialize() throws SQLException {  }

  /*  @FXML
    void ModifierUser(ActionEvent event) throws SQLException {
        if (event.getSource()==btnModifier)
        {
            int userId = Integer.parseInt(tfIdD.getText());
            String nom = tfNomM.getText();
            String prenom = tfPrenomM.getText();
            String adresse = tfAdresseM.getText();
            String email = tfEmailM.getText();
            String mdp = tfMdpM.getText();
            int tel= Integer.parseInt(tfTelM.getText());

            User user =userService.getUserDetailsById(userId);
            user.setNom(nom);
            user.setPrenom(prenom);
            user.setIdA(user.getIdA());
            user.setEmail(email);
            user.setMdp(mdp);
            user.setTel(tel);
            try {
                userService.modifier(user);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("vos données sont modifiés");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Something wrong");
                alert.showAndWait();
            }
        }

    }*/

   /* @FXML
    void SwitchFromDetailsToMod(ActionEvent event) throws SQLException {
        if(event.getSource()==btnSwitchToModif){
            int userId = Integer.parseInt(tfIdD.getText());
            paneDetails.toBack();
            User user = userService.getUserDetailsById(userId);


            tfNomM.setText(user.getNom());
            tfPrenomM.setText(user.getPrenom());
            tfEmailM.setText(user.getEmail());
            tfMdpM.setText(user.getMdp());
            tfTelM.setText(String.valueOf(user.getTel()));




            }


    }*/

    @FXML
    void UserClicked(MouseEvent event) {

    }


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/yumlink/firstPage.fxml"));
        try {
            Parent root=loader.load();
            Scene scene=new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnBackClicked(MouseEvent event) throws SQLException {
        if(event.getSource()==btnBack){
            paneModify.toBack();
            sessionManager userSession = ApplicationContext.getInstance().getUsersession();
            int id=userSession.getIdU();
            int idA= userSession.getIdA();
            User user=userService.getUserDetailsById(id);
            Adresse adresse=adresseService.getAdresseDetailsById(idA);

            setUserDetails(user);
            setAdresseDetails(adresse);
            

        }
    }
    @FXML
    void logOutClicked(MouseEvent event) throws IOException {
if (event.getSource()==logoutImage){
    Parent root = FXMLLoader.load(getClass().getResource("/com/example/yumlink/login.fxml"));
    Stage window = (Stage) logoutImage.getScene().getWindow();
    window.setScene(new Scene(root,1200,600));
}
    }
    public void setUserDetails(User user){

        tfNomD.setText(user.getNom());
        tfPrenomD.setText(user.getPrenom());
        tfEmailD.setText(user.getEmail());
        tfMdpD.setText(user.getMdp());
        tfTelD.setText(String.valueOf(user.getTel()));


    }
    public void setAdresseDetails(Adresse adresse){
        tfGouvD.setText(adresse.getGouvernorat());
        tfVilleD.setText(adresse.getVille());
        tfRueD.setText(adresse.getRue());
        tfCodePostalD.setText(String.valueOf(adresse.getCodePostal()));

    }
    @FXML
    void btnMyAccountClicked(ActionEvent event) throws SQLException {
if(event.getSource()==btnMyAccount) {
    System.out.println("temchi");
    paneDetails.toFront();
    sessionManager userSession = ApplicationContext.getInstance().getUsersession();
    int id=userSession.getIdU();
    int idA= userSession.getIdA();
    User user=userService.getUserDetailsById(id);
    Adresse adresse=adresseService.getAdresseDetailsById(idA);
   setUserDetails(user);
   setAdresseDetails(adresse);
}

    }
    @FXML
    void SwitchFromDetailsToMod(ActionEvent event) throws SQLException{
        paneModify.toFront();
        sessionManager userSession = ApplicationContext.getInstance().getUsersession();
        int id=userSession.getIdU();
        int idA= userSession.getIdA();
        User user=userService.getUserDetailsById(id);
        Adresse adresse=adresseService.getAdresseDetailsById(idA);
        tfNomM.setText(user.getNom());
        tfPrenomM.setText(user.getPrenom());
        tfEmailM.setText(user.getEmail());
        tfMdpM.setText(user.getMdp());
        tfTelM.setText(String.valueOf(user.getTel()));
        tfGouverM.setText(adresse.getGouvernorat());
        tfVilleM.setText(adresse.getVille());
        tfRueM.setText(adresse.getRue());
        tfcodePM.setText(String.valueOf(adresse.getCodePostal()));

    }

    public void ModifierUser(ActionEvent event) throws SQLException {
        if (event.getSource()==btnModifier)
        {
            String nom = tfNomM.getText();
            String prenom = tfPrenomM.getText();
            String email = tfEmailM.getText();
            String mdp = tfMdpM.getText();
            int tel= Integer.parseInt(tfTelM.getText());
            String gouvernorat=tfGouverM.getText();
            String ville=tfVilleM.getText();
            String rue=tfRueM.getText();
            int codePostal= Integer.parseInt(tfcodePM.getText());
            sessionManager userSession = ApplicationContext.getInstance().getUsersession();
            int id=userSession.getIdU();
            int idA= userSession.getIdA();
            User user=userService.getUserDetailsById(id);
            Adresse adresse=adresseService.getAdresseDetailsById(idA);
            user.setNom(nom);
            user.setPrenom(prenom);
            //user.setIdA(user.getIdA());
            user.setEmail(email);
            user.setMdp(mdp);
            user.setTel(tel);
            adresse.setGouvernorat(gouvernorat);
            adresse.setVille(ville);
            adresse.setRue(rue);
            adresse.setCodePostal(codePostal);
            try {
                adresseService.modifierAdresse(adresse);
                userService.modifier(user);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("vos données sont modifiés");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Something wrong");
                alert.showAndWait();
            }
        }
    }
}
