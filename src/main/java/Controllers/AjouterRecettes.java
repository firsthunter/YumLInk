package Controllers;

import Entities.Recettes;
import Services.RecettesS;
import com.almasb.fxgl.app.MainWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utiles.DataBase;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class AjouterRecettes implements Initializable {
    @FXML
    private TextField tf_Calorie;

    @FXML
    private TextField tf_desc;

    @FXML
    private TextField tf_image;

    @FXML
    private TextField tf_nom;

    @FXML
    private TextField tf_prot;

    @FXML
    private TextField tf_video;
    @FXML
    private Button Btn_update;

    @FXML
    private TableColumn<Recettes, Integer> Col_Cal;

    @FXML
    private TableColumn<Recettes, String> Col_Desc;

    @FXML
    private TableColumn<Recettes, String> Col_Img;

    @FXML
    private TableColumn<Recettes, String> Col_Nom;

    @FXML
    private TableColumn<Recettes, Integer> Col_Prot;

    @FXML
    private TableColumn<Recettes, String> Col_Vid;

    @FXML
    private TableColumn<Recettes, Integer> Col_id;

    @FXML
    private TableView<Recettes> Table;

    @FXML
    private Button btn_delete;
    /*@FXML
    private ComboBox<String> comboB;*/

    @FXML
    private TextField tf_ing;
    @FXML
    private ImageView imgC;
    @FXML
    private Button AddImg;
    @FXML
    private Button addp;
    private String path  ="";
    Connection  conn= DataBase.getInstance().getConn() ;


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
        Recettes r = new Recettes(tf_nom.getText(), tf_desc.getText(), path , Integer.parseInt(tf_Calorie.getText()),Integer.parseInt(tf_prot.getText()))  ;
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


    }

    public void showRecettes()  {

        ObservableList<Recettes> list = getRecettes();
        // Add columns for delete and update buttons
        TableColumn<Recettes, Void> deleteCol = new TableColumn<>("Delete");
        TableColumn<Recettes, Void> updateCol = new TableColumn<>("Update");

        // Set cell factories for delete and update columns
        deleteCol.setCellFactory(param -> new TableCell<Recettes, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Recettes recette = getTableView().getItems().get(getIndex());
                    handleDeleteAction(recette);
                });
            }

           @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        updateCol.setCellFactory(param -> new TableCell<Recettes, Void>() {
            private final Button updateButton = new Button("Update");

            {
                updateButton.setOnAction(event -> {
                    Recettes recette = getTableView().getItems().get(getIndex());
                    //handleUpdateAction(recette);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                }
            }
        });

        // Add the delete and update columns to the TableView
        //Table.getColumns().addAll(deleteCol);

        Col_id.setCellValueFactory(new PropertyValueFactory<Recettes , Integer>("id_r"));
        Col_Nom.setCellValueFactory(new PropertyValueFactory<Recettes , String>("nom"));
        Col_Desc.setCellValueFactory(new PropertyValueFactory<Recettes , String>("description"));
        Col_Img.setCellValueFactory(new PropertyValueFactory<Recettes , String>("imgSrc"));
        Col_Cal.setCellValueFactory(new PropertyValueFactory<Recettes , Integer>("calorie"));
        Col_Prot.setCellValueFactory(new PropertyValueFactory<Recettes , Integer>("protein"));



        Table.setItems(list);
    }
    private void handleDeleteAction(Recettes recette) {
        // Display a confirmation dialog to confirm deletion
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Are you sure you want to delete this recipe?");

        // Show the confirmation dialog and wait for user input
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // If the user clicks OK, proceed with deletion
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Call the service or repository method to delete the recipe
                RecettesS rs = new RecettesS();
                rs.supprimer(recette.getId_r());

                // Refresh the TableView to reflect the deletion
                showRecettes();
            } catch (SQLException e) {
                e.printStackTrace();
                // Display an error message or handle the exception as needed
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Deletion Failed");
                errorAlert.setContentText("An error occurred while deleting the recipe. Please try again.");
                errorAlert.showAndWait();
            }
        }
    }
    @FXML
    /*private void handleUpdateAction(Recettes recette) {

        if (recette != null) {
            try {
                // Load the FXML file for the update dialog
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierAdmin.fxml"));
                Parent root = loader.load();

                // Get the controller for the update dialog
                ModifierAdmin controller = loader.getController();

                // Pass the selected recipe to the controller
                controller.setRecipe(recette);

                // Create a new dialog with the update dialog as the content
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.getDialogPane().setContent(root);
                dialog.setTitle("Update Recipe");

                // Add buttons to the dialog
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

                // Show the dialog and wait for user input
                Optional<ButtonType> result = dialog.showAndWait();

                // If the user clicks OK, update the recipe
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    //controller.updateRecipe();

                    // Refresh the TableView to reflect the changes
                    showRecettes();
                }
            } catch (IOException e) {
                e.printStackTrace();
                // Handle any exceptions that occur while loading the update dialog
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Update Failed");
                errorAlert.setContentText("An error occurred while loading the update dialog. Please try again.");
                errorAlert.showAndWait();
            }
        } else {
            // If no recipe is selected, display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a recipe to update.");
            alert.showAndWait();
        }
    }*/


    private void handleRowClick(MouseEvent event) {
        if (!Table.getSelectionModel().isEmpty()) {
            Recettes selectedRecette = Table.getSelectionModel().getSelectedItem();
            tf_nom.setText(selectedRecette.getNom());
            tf_desc.setText(selectedRecette.getDescription());
            //imgC.setImage(selectedRecette.getImgSrc());

            tf_Calorie.setText(Integer.toString(selectedRecette.getCalorie()));
            tf_prot.setText(Integer.toString(selectedRecette.getProtein()));

        }
    }

    @FXML
    void Delete(ActionEvent event) {
        Recettes selectedRecette = Table.getSelectionModel().getSelectedItem();

        if (selectedRecette != null) {
            // Delete the selected item from the database
            try {
                RecettesS rs = new RecettesS();
                rs.supprimer(selectedRecette.getId_r());

                // Remove the item from the table's data
                Table.getItems().remove(selectedRecette);
                showRecettes() ;
            } catch (SQLException e) {
                // Handle any SQL exception that occurs during deletion
                e.printStackTrace();
            }
        } else {
            // If no item is selected, display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a recette to delete.");
            alert.show();
        }
    }

    @FXML
    void Update(ActionEvent event) {
        Recettes selectedRecette = Table.getSelectionModel().getSelectedItem();

        if (selectedRecette != null) {
            // Update the selected item's properties with the values entered in the text fields
            selectedRecette.setNom(tf_nom.getText());
            selectedRecette.setDescription(tf_desc.getText());
            selectedRecette.setCalorie(Integer.parseInt(tf_Calorie.getText()));
            selectedRecette.setProtein(Integer.parseInt(tf_prot.getText()));
            selectedRecette.setImgSrc(tf_image.getText());


            try {
                // Call the modifier method to update the record in the database
                RecettesS rs = new RecettesS();
                rs.modifier(selectedRecette);

                // Refresh the table view to reflect the changes
                showRecettes();
            } catch (SQLException e) {
                // Handle any SQL exception that occurs during update
                e.printStackTrace();
            }
        } else {
            // If no item is selected, display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a recette to update.");
            alert.show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


            showRecettes();
        Table.setOnMouseClicked(this::handleRowClick);
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
    void Addimg(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String imagePath = file.toURI().toString();
            System.out.println("Image Path: " + imagePath); // Debugging: Print out image path

            try {
                Image image = new Image(imagePath);
                imgC.setImage(image);
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

    @FXML
    void add(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddRecetteChef.fxml"));
            Parent root = loader.load();

            // Set the FXML file as the root of the scene
            Scene scene = new Scene(root);

            // Get the current stage from the button's scene
            Stage stage = (Stage) addp.getScene().getWindow();

            // Set the scene to the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
    void listeIng(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/IngredientAdmin.fxml"));
            Parent root = loader.load();

            // Set the FXML file as the root of the scene
            Scene scene = new Scene(root);

            // Get the current stage from the button's scene
            Stage stage = (Stage) addp.getScene().getWindow();

            // Set the scene to the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }


