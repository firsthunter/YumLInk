package Controllers;

import Entities.Recettes;
import Services.RecettesS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;

public class ModifierAdmin {

    @FXML
    private Button AddImg_m;

    @FXML
    private Button Btn_update_m;

    @FXML
    private ComboBox<?> comboB;

    @FXML
    private ImageView imgC_m;

    @FXML
    private TextField tf_Calorie_m;

    @FXML
    private TextField tf_desc_m;

    @FXML
    private TextField tf_ing_m;

    @FXML
    private TextField tf_nom_m;

    @FXML
    private TextField tf_prot_m;
    private String imageP = "" ;
     Recettes rs ;
    AffichageChef afficher  ;
    @FXML
    void Addimgm(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String imagePath = file.toURI().toString();
            System.out.println("Image Path: " + imagePath); // Debugging: Print out image path

            try {
                Image image = new Image(imagePath);
                imgC_m.setImage(image);
                // Set the image to the ImageView
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error loading image: " + e.getMessage());
                alert.show();
            }

        }
        imageP = file.getAbsolutePath();
    }




    public void initData(Recettes rs,   AffichageChef afficher)
    {
        this.rs  = rs ;
        this.afficher = afficher ;
        populateFields () ;
    }
    private void populateFields () {
        tf_nom_m.setText(rs.getNom());
        tf_desc_m.setText(rs.getDescription());
        tf_Calorie_m.setText(Integer.toString(rs.getCalorie()));
        tf_prot_m.setText(Integer.toString(rs.getProtein()));
        File  file = new File(rs.getImgSrc()) ;
        if (file.exists()){
            Image image = new Image(file.toURI().toString()) ;
            imgC_m.setImage(image);
        }

    }
    @FXML
         public   void Update(ActionEvent event) {
        // Check if the selected recipe is not null
        if (rs != null) {
            // Check if any of the required fields are empty
            if (tf_nom_m.getText().isEmpty() || tf_desc_m.getText().isEmpty() || tf_prot_m.getText().isEmpty() || tf_Calorie_m.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill in all the required fields.");
                alert.show();
                return; // Exit the method if any required field is empty
            }

            // Check if the calorie and protein fields contain valid numeric values
            try {
                int calorie = Integer.parseInt(tf_Calorie_m.getText());
                int protein = Integer.parseInt(tf_prot_m.getText());

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

            // If all input is valid, proceed to update the recipe
            try {
                // Set the updated data for the recipe
                rs.setNom(tf_nom_m.getText());
                rs.setDescription(tf_desc_m.getText());
                rs.setProtein(Integer.parseInt(tf_prot_m.getText()));
                rs.setCalorie(Integer.parseInt(tf_Calorie_m.getText()));

                // If a new image is selected, update the image source
                if (!imageP.isEmpty()) {
                    rs.setImgSrc(imageP);
                }

                // Call the service class to update the recipe in the database
                RecettesS RS = new RecettesS();
                RS.modifier(rs);

                // Close the update dialog window
                Stage stage = (Stage) Btn_update_m.getScene().getWindow();
                stage.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle update error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("An error occurred while updating the recipe.");
                alert.show();
            }
        } else {
            // If no recipe is selected, display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a recipe to update.");
            alert.show();
        }

    }


}
