package Controllers;

import Entities.Ingredient;
import Entities.Recettes;
import Services.IngredientS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utiles.DataBase;

import java.sql.*;

public class IngredientAdmin {
    @FXML
    private TableView<Ingredient> TableIngredient;

    @FXML
    private Button addp;

    @FXML
    private Button btn_add_ing;

    @FXML
    private Button btn_update_ing;

    @FXML
    private TableColumn<?, ?> col_delete_ing;

    @FXML
    private TableColumn<Ingredient, Integer> col_id_ing;

    @FXML
    private TableColumn<Ingredient, String> col_nom_ing;

    @FXML
    private TableColumn<Ingredient, Integer> col_quantite_ing;

    @FXML
    private TextField tf_nomIng;

    @FXML
    private TextField tf_quantite;

    private IngredientS ingredientService = new IngredientS();
    Connection conn= DataBase.getInstance().getConn() ;

    @FXML
    private Button listRecette;

    @FXML
    void initialize() throws SQLException {
        // Set cell value factories for table columns
        TableIngredient.setOnMouseClicked(this::handleRowClick);
        col_id_ing.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nom_ing.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_quantite_ing.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        afficher () ;
        // Load ingredients into the table

    }
    private void handleRowClick(MouseEvent event) {
        if (!TableIngredient.getSelectionModel().isEmpty()) {
            Ingredient selectedIngredient = TableIngredient.getSelectionModel().getSelectedItem();
            tf_nomIng.setText(selectedIngredient.getNom());
            tf_quantite.setText(Integer.toString(selectedIngredient.getQuantite()));


        }
    }
    @FXML
    public ObservableList<Ingredient> getIngredient () {
        ObservableList <Ingredient> ing = FXCollections.observableArrayList();
        String query = "SELECT * FROM ingredient" ;
        Statement st ;
        ResultSet rs ;

        //  st = conn.prepareStatement(query);
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Ingredient i ;


            while (rs.next())
            {
                Ingredient r = new Ingredient() ;
                r.setId_ing(rs.getInt("id_ing"));
                r.setNom(rs.getString("nom"));
                r.setQuantite(rs.getInt("quantite"));


                ing.add(r) ;
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e) ;
        }
        return ing ;
    }

    @FXML
    void Add_ing(ActionEvent event) {
        try {
            // Check if the quantity field is not empty
            if (tf_nomIng.getText().isEmpty() || tf_quantite.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill in all the required fields.");
                alert.showAndWait();
                return;
            }

            // Check if the quantity field contains a valid numeric value
            int quantite = 0;
            try {
                quantite = Integer.parseInt(tf_quantite.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a valid numeric value for quantity.");
                alert.showAndWait();
                return;
            }

            // Check if the numeric value is valid (e.g., not negative)
            if (quantite < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a positive value for quantity.");
                alert.showAndWait();
                return;
            }

            // If all input is valid, proceed to add the ingredient
            // Get data from text fields
            String nom = tf_nomIng.getText();

            // Create a new ingredient
            Ingredient ingredient = new Ingredient(nom, quantite);

            // Add the ingredient to the database
            ingredientService.ajouter(ingredient);

            // Refresh the table
            //loadIngredients();

            // Clear text fields
            tf_nomIng.clear();
            tf_quantite.clear();
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("An error occurred while adding the ingredient.");
            alert.showAndWait();
        }
    }





    @FXML
    void Update_ing(ActionEvent event) {
// Get the selected ingredient
        Ingredient selectedIngredient = TableIngredient.getSelectionModel().getSelectedItem();

        if (selectedIngredient != null) {
            try {
                // Get updated data from text fields
                String nom = tf_nomIng.getText();
                int quantite = Integer.parseInt(tf_quantite.getText());

                // Update the selected ingredient
                selectedIngredient.setNom(nom);
                selectedIngredient.setQuantite(quantite);

                // Update the ingredient in the database
                ingredientService.modifier(selectedIngredient);

                // Refresh the tableafficher();

                // Clear text fields
                tf_nomIng.clear();
                tf_quantite.clear();
            } catch (NumberFormatException e) {
                // Handle the case where the quantity is not a valid integer
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a valid quantity.");
                alert.showAndWait();
            } catch (SQLException e) {
                // Handle SQL exceptions
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("An error occurred while updating the ingredient.");
                alert.showAndWait();
            }
        } else {
            // If no ingredient is selected, display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select an ingredient to update.");
            alert.showAndWait();
        }
    }
    public ObservableList<Ingredient> getAllIngredients() throws SQLException {
        ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
        String query = "SELECT * FROM ingredient";

        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                int quantite = resultSet.getInt("quantite");
                Ingredient ingredient = new Ingredient(id, nom, quantite);
                ingredients.add(ingredient);
            }
        }

        return ingredients;
    }
    public void afficher () throws SQLException {

        ObservableList<Ingredient> list = getIngredient();
        col_id_ing.setCellValueFactory(new PropertyValueFactory<Ingredient, Integer>("id_ing"));
        col_nom_ing.setCellValueFactory(new PropertyValueFactory<Ingredient , String>("nom"));
        col_quantite_ing.setCellValueFactory(new PropertyValueFactory<Ingredient , Integer>("Quantite"));

        TableColumn<Ingredient, Void> col_delete_ing = new TableColumn<>("Delete");
        col_delete_ing.setCellFactory(param -> new TableCell<Ingredient, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Ingredient ingredient = getTableView().getItems().get(getIndex());
                    deleteIngredient(ingredient); // Implement this method to delete the ingredient
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

        // Add the delete button column to the TableView
        TableIngredient.getColumns().add(col_delete_ing);

        // Set the items to the TableView
        TableIngredient.setItems(list);
    }
    @FXML
    void deleteIngredient(Ingredient ingredient) {
        try {
            // Call the service or repository method to delete the ingredient
            ingredientService.supprimer(ingredient.getId_ing());

            // Optionally, update the UI to reflect the deletion
            // For example, remove the ingredient from the table's data
            TableIngredient.getItems().remove(ingredient);
        } catch (SQLException e) {
            // Handle any SQL exception that occurs during deletion
            e.printStackTrace();
            // Display an error message or handle the exception as needed
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Deletion Failed");
            errorAlert.setContentText("An error occurred while deleting the ingredient. Please try again.");
            errorAlert.showAndWait();
        }
    }
    @FXML
    void ListRecette(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PageAdminR.fxml"));
            Parent root = loader.load();

            // Set the FXML file as the root of the scene
            Scene scene = new Scene(root);

            // Get the current stage from the button's scene
            Stage stage = (Stage) listRecette.getScene().getWindow();

            // Set the scene to the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
