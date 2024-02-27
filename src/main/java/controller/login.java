package controller;

import entities.Adresse;
import entities.ApplicationContext;
import entities.User;
import entities.sessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.AdresseService;
import service.UserService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class login implements Initializable {
    UserService userService = new UserService();
    AdresseService adresseService=new AdresseService();
    @FXML
    private ImageView btnBackLogin;

    @FXML
    private Button btnImage;

    @FXML
    private Button btnInscrip;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignUp;

    @FXML
    private ComboBox<String> cbRole;

    @FXML
    private Pane paneSignIn;

    @FXML
    private Pane paneSignUp;

    @FXML
    private TextField tfCodePostal;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfEmailSU;

    @FXML
    private TextField tfGouv;

    @FXML
    private TextField tfImagePath;

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfPrenom;

    @FXML
    private TextField tfRue;

    @FXML
    private TextField tfTel;

    @FXML
    private TextField tfVille;

    @FXML
    private PasswordField tfpwdL;

    @FXML
    private PasswordField tfpwdSu;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> list=FXCollections.observableArrayList("chef","client");
        cbRole.setItems(list);
    }

    @FXML

    void SignUpAction(ActionEvent event) {
        if(event.getSource().equals(btnSignUp)){
            paneSignUp.toFront();
            System.out.println("c bon");
        }

    }



    @FXML
    void backClicked(MouseEvent event) {
        if(event.getSource().equals(btnBackLogin)){
            paneSignIn.toFront();
        }
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        if (event.getSource() == btnLogin) {

        String email=tfEmail.getText();
        String mdp=tfpwdL.getText();
        try{
            if(userService.isAdmin(email,mdp)){
                Parent root = FXMLLoader.load(getClass().getResource("/com/example/yumlink/adminU.fxml"));
                Stage window = (Stage) btnLogin.getScene().getWindow();
                window.setScene(new Scene(root,1200,600));
            }else {
                User user=userService.getUserByEmailAndPwd(email,mdp);
                if(user!=null){
                    sessionManager userSession=sessionManager.getInstace(user.getIdU(),user.getEmail(), user.getIdA());
                    ApplicationContext.getInstance().setUserSession(userSession);
                    System.out.println(userSession.toString());
                    if(user.getRole().equals("client")){


                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/yumlink/welcomePage.fxml"));

                            Parent root = loader.load();
                            Scene scene = new Scene(root);
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(scene);

                           /* firstPage controller = loader.getController();
                            controller.setUserDetails(user);*/

                            stage.show();

                    }else if(user.getRole().equals("chef")) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/yumlink/welcomePage.fxml"));

                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);

                        // Access the controller of firstPage.fxml and set user details
                       /* firstPage controller = loader.getController();
                        controller.setUserDetails(user);
                        Adresse adresse=adresseService.getAdresseDetailsById(user.getIdA());
                        controller.setAdresseDetails(adresse);*/

                        stage.show();
                    }
                }else {   Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Something wrong");
                    alert.showAndWait();}
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }}
    @FXML
    void SinscrireAction(ActionEvent event) throws SQLException {
        if (event.getSource() == btnInscrip) {
            String telText = tfTel.getText();


            if (telText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Something wrong");
                alert.showAndWait();}
            String nom = tfNom.getText();
            String prenom = tfPrenom.getText();

            String email = tfEmailSU.getText();
            String mdp = tfpwdSu.getText();
            String Image=tfImagePath.getText();
            String gouvernorat=tfGouv.getText();
            String ville=tfVille.getText();
            String rue=tfRue.getText();
            int codePostal=Integer.parseInt(tfCodePostal.getText());
            int telSu = Integer.parseInt(tfTel.getText());
            String role =cbRole.getSelectionModel().getSelectedItem().toString();
           if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mdp.isEmpty()|| cbRole.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("non");
                alert.showAndWait();
            }
            Adresse a=new Adresse(gouvernorat,ville,rue,codePostal);
            int ida=adresseService.ajouterAdresse(a);
            User u = new User(nom, prenom, email, mdp, telSu, role,ida,Image);
            try {
                userService.ajouter(u);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("user added successfully");
                alert.showAndWait();
                viderTextField();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Something wrong");
                alert.showAndWait();
            }
        }
    }
    public void viderTextField(){
        tfNom.clear();
        tfPrenom.clear();
        tfEmailSU.clear();
        tfpwdSu.clear();
        tfTel.clear();
        cbRole.getSelectionModel().clearSelection();
        tfImagePath.clear();
        tfGouv.clear();
        tfVille.clear();
        tfCodePostal.clear();
        tfRue.clear();
    }
    @FXML
    void getImagePath(ActionEvent event) {
        if (event.getSource() == btnImage) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir une image");
            Stage stage = (Stage) btnImage.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                String imagePath = selectedFile.getAbsolutePath();
                tfImagePath.setText(imagePath); // Mettez à jour le TextField avec le chemin de l'image
            }
        }
    }


}
