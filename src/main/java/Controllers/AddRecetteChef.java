package Controllers;

import Entities.Recettes;
import Services.RecettesS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import utiles.DataBase;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AddRecetteChef implements Initializable {

    @FXML
    private Button AddImg;

    @FXML
    private Button addChef;

    @FXML
    private ComboBox<?> comboB;

    @FXML
    private ImageView imgC_c;

    @FXML
    private TextField tf_Calorie_c;

    @FXML
    private TextField tf_desc_c;

    @FXML
    private TextField tf_ing;

    @FXML
    private TextField tf_nom_c;

    @FXML
    private TextField tf_prot_c;
    private String path  ="";

    Connection conn= DataBase.getInstance().getConn() ;
    @FXML
    private TextField tf_video;
    @FXML
    public ObservableList<Recettes> getRecettes () {
        ObservableList <Recettes> recettes = FXCollections.observableArrayList();
        String query = "SELECT * FROM recettes" ;
        Statement st ;
        ResultSet rs ;

        //  st = conn.prepareStatement(query);
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Recettes recette ;


            while (rs.next())
            {
                Recettes r = new Recettes() ;
                r.setId_r(rs.getInt("id_r"));
                r.setNom(rs.getString("nom"));
                r.setDescription(rs.getString("description"));
                r.setCalorie(rs.getInt("calorie"));
                r.setProtein(rs.getInt("protein"));
                r.setImgSrc(rs.getString("imgSrc"));
                recettes.add(r) ;
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e) ;
        }
        return recettes ;
    }
    @FXML
    void Ajouter(ActionEvent event) {
        // Check if any of the required fields are empty
        if (tf_nom_c.getText().isEmpty() || tf_desc_c.getText().isEmpty() || tf_Calorie_c.getText().isEmpty() || tf_prot_c.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in all the required fields.");
            alert.show();
            return; // Exit the method if any required field is empty
        }

        // Check if the calorie and protein fields contain valid numeric values
        try {
            int calorie = Integer.parseInt(tf_Calorie_c.getText());
            int protein = Integer.parseInt(tf_prot_c.getText());

            // Check if the numeric values are valid (e.g., not negative)
            if (calorie < 0 || protein < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter positive values for calorie and protein.");
                alert.show();
                return; // Exit the method if any value is invalid
            }
        } catch (NumberFormatException e) {
            // Handle the case where the calorie or protein field contains non-numeric characters
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter valid numeric values for calorie and protein.");
            alert.show();
            return; // Exit the method if any value is invalid
        }

        // If all input is valid, proceed to add the recipe
        Recettes r = new Recettes(tf_nom_c.getText(), tf_desc_c.getText(), path , Integer.parseInt(tf_Calorie_c.getText()), Integer.parseInt(tf_prot_c.getText()));
        RecettesS rs = new RecettesS();
        try {
            rs.ajouter(r);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Recipe added successfully.");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error adding recipe: " + e.getMessage());
            alert.show();
        }



    }
   /* @FXML
    void Ajouter(ActionEvent event) {
        Recettes r = new Recettes(tf_nom_c.getText(), tf_desc_c.getText(), path , Integer.parseInt(tf_Calorie_c.getText()),Integer.parseInt(tf_prot_c.getText()))  ;
        RecettesS rs = new RecettesS() ;
        try {
            rs.ajouter(r);
            Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
            alert.setContentText("Added");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR) ;
            alert.setContentText(e.getMessage());
            alert.show();
        }


    }*/

  /* public void showRecettes()  {

        ObservableList<Recettes> list = getRecettes();

        Col_id.setCellValueFactory(new PropertyValueFactory<Recettes , Integer>("id_r"));
        Col_Nom.setCellValueFactory(new PropertyValueFactory<Recettes , String>("nom"));
        Col_Desc.setCellValueFactory(new PropertyValueFactory<Recettes , String>("description"));
        Col_Img.setCellValueFactory(new PropertyValueFactory<Recettes , String>("imgSrc"));
        Col_Vid.setCellValueFactory(new PropertyValueFactory<Recettes , String>("videoSrc"));
        Col_Cal.setCellValueFactory(new PropertyValueFactory<Recettes , Integer>("calorie"));
        Col_Prot.setCellValueFactory(new PropertyValueFactory<Recettes , Integer>("protein"));



        Table.setItems(list);
    }
*/






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


       // showRecettes();
       // Table.setOnMouseClicked(this::handleRowClick);
        /*setCombo(comboB);
        comboB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Update the TextField with the selected item
            if (newValue != null) {
                tf_ing.setText(newValue);
            }
        });*/
    }
    public void setCombo ( ComboBox<String> comboBox)
    {
        ObservableList<String> ingredientList = FXCollections.observableArrayList();
        String query = "SELECT NOM FROM ingredient" ;
        Statement st ;
        ResultSet rs ;

        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            {

                // Iterate over the result set and add ingredient names to the list
                while (rs.next()) {
                    String ingredientName = rs.getString("nom");
                    ingredientList.add(ingredientName);
                }
            } }
        catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions that occur during data retrieval
        }
        comboBox.setItems(ingredientList);
    }
    @FXML
    void Addimgc(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String imagePath = file.toURI().toString();
            System.out.println("Image Path: " + imagePath); // Debugging: Print out image path

            try {
                Image image = new Image(imagePath);
                imgC_c.setImage(image);
                // Set the image to the ImageView
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error loading image: " + e.getMessage());
                alert.show();
            }

        }
        path = file.getAbsolutePath();
    }
}
