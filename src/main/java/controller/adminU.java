package controller;

import entities.User;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.UserService;

import java.io.IOException;
import java.sql.SQLException;

public class adminU  {

    UserService userService = new UserService();
    @FXML
    private TableColumn<User,String> AdresseCol;

    @FXML
    private Pane AfficherUserPane;

    @FXML
    private TableColumn<User,String> EmailCol;

    @FXML
    private TableColumn<User,String> NomCol;

    @FXML
    private Pane SideBarPane;

    @FXML
    private TableColumn<User,Integer> idUcol;

    @FXML
    private TableColumn<User,String> mdpCol;

    @FXML
    private TableColumn<User,String> prenomCol;

    @FXML
    private TableColumn<User,String> roleCol;

    @FXML
    private TableView<User> tableviewUser;

    @FXML
    private TableColumn<User,Integer> telCol;
    @FXML
    private Button btndeleteUserA;
    @FXML
    private ImageView logoutbtn;

    @FXML
public void initialize(){
    try {
        ObservableList<User> observableList= FXCollections.observableList(userService.consulter());
        tableviewUser.setItems(observableList);
        idUcol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getIdU()).asObject());
        NomCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNom()));
        prenomCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPrenom()));
        EmailCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEmail()));
        mdpCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMdp()));
        telCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getTel()).asObject());
        telCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getIdA()).asObject());
        roleCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getRole()));

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}


    @FXML
    void deleteUser(ActionEvent event) throws SQLException {
        if (event.getSource() == btndeleteUserA){
            User selectedUser = tableviewUser.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                int userId = selectedUser.getIdU();

                // Appelez votre fonction de suppression du service avec l'ID
                userService.supprimer(userId);

                ObservableList<User> updatedList = FXCollections.observableList(userService.consulter());
                tableviewUser.setItems(updatedList);

    }
    }
}
    @FXML
    void logout(MouseEvent event) throws IOException {
        if (event.getSource()==logoutbtn){
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/yumlink/login.fxml"));
            Stage window = (Stage) logoutbtn.getScene().getWindow();
            window.setScene(new Scene(root,1200,600));
    }
}}
